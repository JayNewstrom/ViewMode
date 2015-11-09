package com.jaynewstrom.viewmodesample;

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
}
