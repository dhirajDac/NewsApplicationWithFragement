package com.vodafone.com.newsapplicationwithfragement;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.LinearLayout;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class SwipeHomeActivityTest {

    @Rule
    public ActivityTestRule<SwipeHomeActivity> mActivityTestRule = new ActivityTestRule<>(SwipeHomeActivity.class);

    @Test
    public void swipeHomeActivityTest() {
        ViewInteraction imageView = onView(
                allOf(withId(com.vodafone.com.newsapplicationwithfragement.R.id.item_news_image),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(LinearLayout.class),
                                        0),
                                0),
                        isDisplayed()));
        imageView.check(matches(isDisplayed()));

        ViewInteraction recyclerView = onView(
                allOf(withId(com.vodafone.com.newsapplicationwithfragement.R.id.activity_main_newsItem),
                        withParent(allOf(withId(com.vodafone.com.newsapplicationwithfragement.R.id.activity_main),
                                withParent(withId(com.vodafone.com.newsapplicationwithfragement.R.id.activity_swipe_view_pager)))),
                        isDisplayed()));
        recyclerView.perform(actionOnItemAtPosition(0, click()));

        ViewInteraction imageView2 = onView(
                allOf(withId(com.vodafone.com.newsapplicationwithfragement.R.id.item_news_image),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(LinearLayout.class),
                                        0),
                                0),
                        isDisplayed()));
        imageView2.check(matches(isDisplayed()));

    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
