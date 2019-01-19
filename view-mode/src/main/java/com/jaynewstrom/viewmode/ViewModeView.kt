package com.jaynewstrom.viewmode

import android.content.Context
import android.support.design.widget.CoordinatorLayout
import android.util.AttributeSet
import android.view.View
import java.util.LinkedHashMap

class ViewModeView : CoordinatorLayout {
    private val cachedViewModes = LinkedHashMap<ViewMode, View>()

    private var currentViewMode: ViewMode? = null
    private var cacheViews: Boolean = false

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

    fun showViewMode(viewMode: ViewMode) {
        if (currentViewMode == null || currentViewMode != viewMode) {
            currentViewMode = viewMode
            val viewToShow = viewForViewMode(viewMode)
            var i = 0
            val childCount = childCount
            while (i < childCount) {
                val childView = getChildAt(i)
                childView.visibility = if (childView === viewToShow) View.VISIBLE else View.GONE
                i++
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
        var view = cachedViewModes[viewMode]
        if (view == null) {
            view = viewMode.createView(this)
            if (cacheViews) {
                cachedViewModes[viewMode] = view
            } else {
                removeAllViews()
            }
            addView(view)
        }
        return view
    }

    fun setCacheViews(cacheViews: Boolean) {
        this.cacheViews = cacheViews
    }
}
