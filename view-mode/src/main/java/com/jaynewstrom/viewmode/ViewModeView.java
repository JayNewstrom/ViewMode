package com.jaynewstrom.viewmode;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

import java.util.LinkedHashMap;
import java.util.Map;

public final class ViewModeView extends CoordinatorLayout {

    private final Map<ViewMode, View> cachedViewModes = new LinkedHashMap<>();

    private ViewMode currentViewMode;
    private boolean cacheViews;

    public ViewModeView(Context context) {
        super(context);
        initialize(null);
    }

    public ViewModeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(attrs);
    }

    public ViewModeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(attrs);
    }

    private void initialize(AttributeSet attrs) {
        if (attrs == null) {
            cacheViews = true;
        } else {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.ViewModeView);
            cacheViews = a.getBoolean(R.styleable.ViewModeView_view_mode_caching, true);
            a.recycle();
        }
    }

    public void showViewMode(ViewMode viewMode) {
        if (currentViewMode == null || !currentViewMode.equals(viewMode)) {
            currentViewMode = viewMode;
            View viewToShow = viewForViewMode(viewMode);
            for (int i = 0, childCount = getChildCount(); i < childCount; i++) {
                View childView = getChildAt(i);
                childView.setVisibility(childView == viewToShow ? View.VISIBLE : View.GONE);
            }
        }
    }

    public ViewMode currentViewMode() {
        return currentViewMode;
    }

    /**
     * This will return the cached view for the given view mode, or create it if it isn't cached.
     */
    public View viewForViewMode(ViewMode viewMode) {
        View view = cachedViewModes.get(viewMode);
        if (view == null) {
            view = viewMode.createView(this);
            if (cacheViews) {
                cachedViewModes.put(viewMode, view);
            } else {
                removeAllViews();
            }
            addView(view);
        }
        return view;
    }

    public void setCacheViews(boolean cacheViews) {
        this.cacheViews = cacheViews;
    }
}
