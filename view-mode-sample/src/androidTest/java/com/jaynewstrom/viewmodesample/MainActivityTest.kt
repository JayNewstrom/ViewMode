package com.jaynewstrom.viewmodesample

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
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
        val orientation = InstrumentationRegistry.getTargetContext().resources.configuration.orientation
        activityRule.activity.requestedOrientation = if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        } else {
            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
    }
}
