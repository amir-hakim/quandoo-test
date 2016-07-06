package com.test.quandoo;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;

import com.test.quandoo.view.GOLHomeActivity;

import org.junit.Rule;
import org.junit.Test;

public class MainActivityInstrumentationTest {

    @Rule
    public ActivityTestRule<GOLHomeActivity> activityTestRule = new ActivityTestRule<GOLHomeActivity>(GOLHomeActivity.class);

    @Test
    public void clickOnStartGameButton() {
        Espresso.onView(ViewMatchers.withId(R.id.buttonStartGame)).perform(ViewActions.click());
    }

}
