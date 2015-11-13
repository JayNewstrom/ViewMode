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

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public final class ViewModeViewTest {

    private Context context;

    @SuppressLint("PrivateResource")
    @Before public void setupContext() {
        // CoordinatorLayout needs a context that extends from Base_Theme_AppCompat
        context = new ContextThemeWrapper(InstrumentationRegistry.getTargetContext(),
                android.support.design.R.style.Base_Theme_AppCompat);
    }

    @Test @UiThreadTest public void ensureCurrentViewModeIsNullByDefault() {
        ViewModeView view = new ViewModeView(context);
        Assert.assertNull(view.currentViewMode());
    }

    @Test @UiThreadTest public void whenCachingIsEnabledEnsureShowingTheViewCachesTheView() {
        ViewModeView view = new ViewModeView(context);
        ViewMode viewMode = mock(ViewMode.class);
        View cachedView = new View(view.getContext());
        when(viewMode.createView(view)).thenReturn(cachedView, new View(view.getContext()));
        view.showViewMode(viewMode);
        Assert.assertEquals(cachedView, view.viewForViewMode(viewMode));
    }

    @Test @UiThreadTest public void whenCachingIsEnabledEnsureTheSameViewIsReturned() {
        ViewModeView view = new ViewModeView(context);
        ViewMode viewMode = mock(ViewMode.class);
        when(viewMode.createView(view)).thenReturn(new View(view.getContext()), new View(view.getContext()));
        Assert.assertEquals(view.viewForViewMode(viewMode), view.viewForViewMode(viewMode));
    }

    @Test @UiThreadTest public void whenCachingIsDisabledEnsureDifferentViewsAreReturned() {
        ViewModeView view = new ViewModeView(context);
        view.setCacheViews(false);
        ViewMode viewMode = mock(ViewMode.class);
        when(viewMode.createView(view)).thenReturn(new View(view.getContext()), new View(view.getContext()));
        Assert.assertNotEquals(view.viewForViewMode(viewMode), view.viewForViewMode(viewMode));
    }

    @Test @UiThreadTest public void ensureChangingViewModesOnlyCreatesTheViewOnce() {
        ViewModeView view = new ViewModeView(context);
        ViewMode viewMode = mock(ViewMode.class);
        when(viewMode.createView(view)).thenReturn(new View(view.getContext()), new View(view.getContext()));
        view.showViewMode(viewMode);
        view.showViewMode(viewMode);
        verify(viewMode, times(1)).createView(view);
    }

    @Test @UiThreadTest public void ensureOnlyOneChildViewIsVisible() {
        ViewModeView view = new ViewModeView(context);
        ViewMode viewModeOne = mock(ViewMode.class);
        when(viewModeOne.createView(view)).thenReturn(new View(view.getContext()));
        ViewMode viewModeTwo = mock(ViewMode.class);
        when(viewModeTwo.createView(view)).thenReturn(new View(view.getContext()));
        view.showViewMode(viewModeOne);
        view.showViewMode(viewModeTwo);
        Assert.assertEquals(2, view.getChildCount());
        int visibleChildren = 0;
        for (int i = 0, childCount = view.getChildCount(); i < childCount; i++) {
            if (view.getChildAt(i).getVisibility() == View.VISIBLE) {
                visibleChildren++;
            }
        }
        Assert.assertEquals(1, visibleChildren);
    }
}
