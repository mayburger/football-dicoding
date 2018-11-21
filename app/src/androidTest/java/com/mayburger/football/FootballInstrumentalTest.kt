package com.mayburger.football


import android.os.Handler
import android.os.Looper
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.pressBack
import android.support.test.espresso.UiController
import android.support.test.espresso.ViewAction
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import android.test.suitebuilder.annotation.LargeTest
import android.view.View
import android.view.ViewGroup
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import com.mayburger.football.espresso.EspressoIdlingResource
import android.support.test.espresso.IdlingRegistry
import android.support.test.espresso.action.ViewActions.swipeDown
import org.junit.After
import org.junit.Before


@LargeTest
@RunWith(AndroidJUnit4::class)
class FootballInstrumentalTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(HomeActivity::class.java)

    @Test
    fun splashActivityTest() {
        Looper.prepare()
        // Wait for data to be loaded
        onView(withId(R.id.recyclerPrev))
                .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        try { Thread.sleep(1500) } catch (e: InterruptedException) { e.printStackTrace() }
        onView(withId(R.id.awayLine)).perform(click())
        onView(withId(R.id.homeLine)).perform(click())
        onView(withId(R.id.favorite)).perform(click())
        pressBack()
        onView(withText("Next")).perform(click())
        try { Thread.sleep(1500) } catch (e: InterruptedException) { e.printStackTrace() }
        onView(withId(R.id.recyclerNext))
                .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(1, click()))
        onView(withId(R.id.awayLine)).perform(click())
        onView(withId(R.id.homeLine)).perform(click())
        onView(withId(R.id.favorite)).perform(click())
        pressBack()
        onView(withText("teams")).perform(click())
        try { Thread.sleep(1500) } catch (e: InterruptedException) { e.printStackTrace() }
        onView(withId(R.id.recTeams))
                .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.favorite)).perform(click())
        onView(withText("PLAYERS")).perform(click())
        onView(withId(R.id.recycler))
                .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        pressBack()
        pressBack()
        onView(withText("favorites")).perform(click())
        onView(withId(R.id.recEvents))
                .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.unfavorite)).perform(click())
        pressBack()
        onView(withId(R.id.swipeRefreshEvents))
                .perform(withCustomConstraints(swipeDown(), isDisplayingAtLeast(85)))
        onView(withText("Teams")).perform(click())
        onView(withId(R.id.recTeams))
                .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.unfavorite)).perform(click())
        pressBack()
        onView(withId(R.id.swipeRefreshTeams))
                .perform(withCustomConstraints(swipeDown(), isDisplayingAtLeast(85)))
    }


    // Register your Idling Resource before any tests regarding this component
    @Before
    fun registerIdlingResource() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingResource)
    }

    // Unregister your Idling Resource so it can be garbage collected and does not leak any memory
    @After
    fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingResource)
    }

    fun withCustomConstraints(action: ViewAction, constraints: Matcher<View>): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return constraints
            }

            override fun getDescription(): String {
                return action.description
            }

            override fun perform(uiController: UiController, view: View) {
                action.perform(uiController, view)
            }
        }
    }

    private fun childAtPosition(
            parentMatcher: Matcher<View>, position: Int): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return (parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position))
            }
        }
    }
}
