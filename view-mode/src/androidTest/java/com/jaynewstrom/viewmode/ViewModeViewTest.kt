package com.jaynewstrom.viewmode

import android.annotation.SuppressLint
import android.content.Context
import android.view.ContextThemeWrapper
import android.view.View
import androidx.test.annotation.UiThreadTest
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import java.util.concurrent.CountDownLatch

@RunWith(AndroidJUnit4::class)
class ViewModeViewTest {

    private lateinit var context: Context

    @SuppressLint("PrivateResource")
    @Before
    fun setupContext() {
        // CoordinatorLayout needs a context that extends from Base_Theme_AppCompat
        context = ContextThemeWrapper(
            ApplicationProvider.getApplicationContext<Context>(),
            androidx.appcompat.R.style.Base_Theme_AppCompat
        )
    }

    @Test @UiThreadTest fun ensureCurrentViewModeIsNullByDefault() {
        val view = ViewModeView(context)
        Assert.assertNull(view.currentViewMode())
    }

    @Test @UiThreadTest fun whenCachingIsEnabledEnsureShowingTheViewCachesTheView() {
        val view = ViewModeView(context)
        val viewMode = mock(ViewMode::class.java)
        val cachedView = View(view.context)
        `when`(viewMode.createView(view)).thenReturn(cachedView, View(view.context))
        view.showViewMode(viewMode)
        Assert.assertEquals(cachedView, view.viewForViewMode(viewMode))
    }

    @Test @UiThreadTest fun whenCachingIsEnabledEnsureTheSameViewIsReturned() {
        val view = ViewModeView(context)
        val viewMode = mock(ViewMode::class.java)
        `when`(viewMode.createView(view)).thenReturn(View(view.context), View(view.context))
        Assert.assertEquals(view.viewForViewMode(viewMode), view.viewForViewMode(viewMode))
    }

    @Test @UiThreadTest fun whenCachingIsDisabledEnsureDifferentViewsAreReturned() {
        val view = ViewModeView(context)
        view.setCacheViews(false)
        val viewMode = mock(ViewMode::class.java)
        `when`(viewMode.createView(view)).thenReturn(View(view.context), View(view.context))
        Assert.assertNotEquals(view.viewForViewMode(viewMode), view.viewForViewMode(viewMode))
    }

    @Test @UiThreadTest fun ensureChangingViewModesOnlyCreatesTheViewOnce() {
        val view = ViewModeView(context)
        val viewMode = mock(ViewMode::class.java)
        `when`(viewMode.createView(view)).thenReturn(View(view.context), View(view.context))
        view.showViewMode(viewMode)
        view.showViewMode(viewMode)
        verify(viewMode, times(1)).createView(view)
    }

    @Test @UiThreadTest fun ensureOnlyOneChildViewIsVisible() {
        val view = ViewModeView(context)
        val viewModeOne = mock(ViewMode::class.java)
        `when`(viewModeOne.createView(view)).thenReturn(View(view.context))
        val viewModeTwo = mock(ViewMode::class.java)
        `when`(viewModeTwo.createView(view)).thenReturn(View(view.context))
        view.showViewMode(viewModeOne)
        view.showViewMode(viewModeTwo)
        Assert.assertEquals(2, view.childCount.toLong())
        var visibleChildren = 0
        var i = 0
        val childCount = view.childCount
        while (i < childCount) {
            if (view.getChildAt(i).visibility == View.VISIBLE) {
                visibleChildren++
            }
            i++
        }
        Assert.assertEquals(1, visibleChildren.toLong())
    }

    @Test @UiThreadTest fun whenCachingIsDisabledEnsureOnlyOneChildIsAddedToTheView() {
        val view = ViewModeView(context)
        view.setCacheViews(false)
        val viewModeOne = mock(ViewMode::class.java)
        `when`(viewModeOne.createView(view)).thenReturn(View(view.context))
        val viewModeTwo = mock(ViewMode::class.java)
        `when`(viewModeTwo.createView(view)).thenReturn(View(view.context))
        view.showViewMode(viewModeOne)
        view.showViewMode(viewModeTwo)
        Assert.assertEquals(1, view.childCount.toLong())
    }

    @Test @UiThreadTest fun transitionFailsWhenAnotherTransitionIsInProgress() {
        val view = ViewModeView(context)
        val viewModeOne = mock(ViewMode::class.java)
        `when`(viewModeOne.createView(view)).thenReturn(View(view.context))
        val viewModeTwo = mock(ViewMode::class.java)
        `when`(viewModeTwo.createView(view)).thenReturn(View(view.context))
        view.showViewMode(viewModeOne, object : ViewModeTransition {
            override fun transition(fromViewMode: ViewMode?, fromView: View?, toView: View, transitionComplete: () -> Unit) {
                // Not calling transitionComplete
            }
        })
        assertThrowsWithMessage<IllegalStateException>("A transition is already occurring.") {
            view.showViewMode(viewModeTwo)
        }
    }

    @Test @UiThreadTest fun transitionSendsCorrectValues() {
        val view = ViewModeView(context)
        val viewModeOne = mock(ViewMode::class.java)
        val viewOne = View(view.context)
        `when`(viewModeOne.createView(view)).thenReturn(viewOne)
        val viewModeTwo = mock(ViewMode::class.java)
        val viewTwo = View(view.context)
        `when`(viewModeTwo.createView(view)).thenReturn(viewTwo)
        val countDownLatch = CountDownLatch(2)
        view.showViewMode(viewModeOne, object : ViewModeTransition {
            override fun transition(fromViewMode: ViewMode?, fromView: View?, toView: View, transitionComplete: () -> Unit) {
                assertNull(fromViewMode)
                assertNull(fromView)
                assertEquals(toView, viewOne)
                transitionComplete()
                countDownLatch.countDown()
            }
        })
        view.showViewMode(viewModeTwo, object : ViewModeTransition {
            override fun transition(fromViewMode: ViewMode?, fromView: View?, toView: View, transitionComplete: () -> Unit) {
                assertEquals(fromViewMode, viewModeOne)
                assertEquals(fromView, viewOne)
                assertEquals(toView, viewTwo)
                transitionComplete()
                countDownLatch.countDown()
            }
        })
        countDownLatch.await()
    }
}
