package com.jaynewstrom.viewmodesample;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public final class MainActivityTest {

    @Rule public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    @Test public void ensureContentViewShowsByDefault() {
        onView(withId(R.id.button)).check(matches(isDisplayed()));
    }

    @Test public void ensureContentViewCanBeSwitched() {
        onView(withId(R.id.button)).perform(click());
        onView(withText(R.string.failed_to_load)).check(matches(isDisplayed()));
    }

    @Test public void onRotationEnsureViewModeIsPersisted() {
        onView(withId(R.id.button)).check(matches(isDisplayed()));
        changeOrientation();
        onView(withId(R.id.button)).perform(click());
        onView(withText(R.string.failed_to_load)).check(matches(isDisplayed()));
        changeOrientation();
        onView(withText(R.string.failed_to_load)).check(matches(isDisplayed()));
    }

    private void changeOrientation() {
        int orientation = InstrumentationRegistry.getTargetContext().getResources().getConfiguration().orientation;
        activityRule.getActivity().setRequestedOrientation((orientation == Configuration.ORIENTATION_PORTRAIT)
                ? ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE : ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }
}
