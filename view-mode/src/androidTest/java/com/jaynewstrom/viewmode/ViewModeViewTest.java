package com.jaynewstrom.viewmode;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.annotation.UiThreadTest;
import android.support.test.runner.AndroidJUnit4;
import android.view.ContextThemeWrapper;
import android.view.View;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public final class ViewModeViewTest {

    private Context context;

    @SuppressLint("PrivateResource")
    @Before public void setupContext() {
        // CoordinatorLayout needs a context that extends from Base_Theme_AppCompat
        context = new ContextThemeWrapper(InstrumentationRegistry.getTargetContext(),
                android.support.design.R.style.Base_Theme_AppCompat);
    }

    @Test @UiThreadTest public void testCurrentViewModeIsNullByDefault() {
        ViewModeView view = new ViewModeView(context);
        Assert.assertNull(view.currentViewMode());
    }

    @Test @UiThreadTest public void testShowViewModeUpdatesCurrentView() {
        ViewModeView view = new ViewModeView(context);
        ViewMode simpleViewMode = new ViewMode() {
            @Override public View createView(ViewModeView parent) {
                return new View(parent.getContext());
            }
        };
        view.showViewMode(simpleViewMode);
        Assert.assertEquals(simpleViewMode, view.currentViewMode());
    }

    @Test @UiThreadTest public void testViewForViewModeReturnsCachedView() {
        ViewModeView view = new ViewModeView(context);
        final View cachedView = new View(view.getContext());
        ViewMode simpleViewMode = new ViewMode() {
            @Override public View createView(ViewModeView parent) {
                return cachedView;
            }
        };
        view.showViewMode(simpleViewMode);
        Assert.assertEquals(cachedView, view.viewForViewMode(simpleViewMode));
    }
}
