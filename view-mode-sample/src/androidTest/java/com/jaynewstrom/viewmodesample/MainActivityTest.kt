package com.jaynewstrom.viewmodesample

import android.content.Context
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @get:Rule var activityRule = ActivityTestRule(MainActivity::class.java)

    @Test fun ensureContentViewShowsByDefault() {
        onView(withId(R.id.button)).check(matches(isDisplayed()))
    }

    @Test fun ensureContentViewCanBeSwitched() {
        onView(withId(R.id.button)).perform(click())
        onView(withText(R.string.failed_to_load)).check(matches(isDisplayed()))
    }

    @Test fun onRotationEnsureViewModeIsPersisted() {
        onView(withId(R.id.button)).check(matches(isDisplayed()))
        changeOrientation()
        onView(withId(R.id.button)).perform(click())
        onView(withText(R.string.failed_to_load)).check(matches(isDisplayed()))
        changeOrientation()
        onView(withText(R.string.failed_to_load)).check(matches(isDisplayed()))
    }

    private fun changeOrientation() {
        val orientation = ApplicationProvider.getApplicationContext<Context>().resources.configuration.orientation
        activityRule.activity.requestedOrientation = if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        } else {
            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
    }
}
