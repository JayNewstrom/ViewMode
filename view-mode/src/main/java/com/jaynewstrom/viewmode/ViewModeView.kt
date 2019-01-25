package com.jaynewstrom.viewmode

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout

class ViewModeView : CoordinatorLayout {
    private val cachedViewModes = LinkedHashMap<ViewMode, View>()

    private var currentViewMode: ViewMode? = null
    private var currentView: View? = null
    private var cacheViews: Boolean = false
    private var transitioning: Boolean = false

    constructor(context: Context) : super(context) {
        initialize(null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initialize(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initialize(attrs)
    }

    private fun initialize(attrs: AttributeSet?) {
        if (attrs == null) {
            cacheViews = true
        } else {
            val a = context.obtainStyledAttributes(attrs, R.styleable.ViewModeView)
            cacheViews = a.getBoolean(R.styleable.ViewModeView_view_mode_caching, true)
            a.recycle()
        }
    }

    fun showViewMode(viewMode: ViewMode, transition: ViewModeTransition = DefaultViewModeTransition) {
        if (transitioning) {
            throw IllegalStateException("A transition is already occurring.")
        }
        if (currentViewMode == null || currentViewMode != viewMode) {
            transitioning = true

            val previousViewMode = currentViewMode
            val previousView = currentView
            currentViewMode = viewMode

            val viewToShow = viewForViewMode(viewMode)
            currentView = viewToShow
            addView(viewToShow)
            transition.transition(previousViewMode, previousView, viewToShow) {
                var i = 0
                while (i < childCount) {
                    val childView = getChildAt(i)

                    if (cacheViews) {
                        childView.visibility = if (childView === viewToShow) View.VISIBLE else View.GONE
                        i++
                    } else {
                        if (childView === viewToShow) {
                            i++
                        } else {
                            removeViewAt(i)
                        }
                    }
                }

                transitioning = false
            }
        }
    }

    fun currentViewMode(): ViewMode? {
        return currentViewMode
    }

    /**
     * This will return the cached view for the given view mode, or create it if it isn't cached.
     */
    fun viewForViewMode(viewMode: ViewMode): View {
        return if (cacheViews) {
            cachedViewModes.getOrPut(viewMode) {
                viewMode.createView(this)
            }
        } else {
            viewMode.createView(this)
        }
    }

    fun setCacheViews(cacheViews: Boolean) {
        this.cacheViews = cacheViews
    }

    fun preloadViewMode(viewMode: ViewMode) {
        if (!cacheViews) {
            throw IllegalStateException("Preloading is only applicable when caching views.")
        }
        // This creates the view, and caches it in our pool, to be added and shown when calling showViewMode.
        viewForViewMode(viewMode)
    }
}
