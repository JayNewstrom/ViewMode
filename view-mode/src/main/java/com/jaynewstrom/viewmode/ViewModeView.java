package com.jaynewstrom.viewmode;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

import java.util.LinkedHashMap;
import java.util.Map;

public final class ViewModeView extends CoordinatorLayout {

    private final Map<ViewMode, View> cachedViewModes = new LinkedHashMap<>();

    private ViewMode currentViewMode;

    public ViewModeView(Context context) {
        super(context);
    }

    public ViewModeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewModeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void showViewMode(ViewMode viewMode) {
        currentViewMode = viewMode;
        View viewToShow = viewForViewMode(viewMode);
        for (int i = 0, childCount = getChildCount(); i < childCount; i++) {
            View childView = getChildAt(i);
            childView.setVisibility(childView == viewToShow ? View.VISIBLE : View.GONE);
        }
    }

    public ViewMode currentViewMode() {
        return currentViewMode;
    }

    /**
     * This will return the cached view for the given view mode, or create it if it doesn't exist.
     */
    public View viewForViewMode(ViewMode viewMode) {
        View view = cachedViewModes.get(viewMode);
        if (view == null) {
            view = viewMode.createView(this);
            view.setVisibility(View.GONE);
            addView(view);
            cachedViewModes.put(viewMode, view);
        }
        return view;
    }
}
