package com.example.codingchallenge.view.activity

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.example.codingchallenge.R
import com.example.codingchallenge.view.adapter.UserAdapter
import org.hamcrest.Matchers.allOf
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class ListActivityTest {


    @Test
    fun testIsActivityInView() {
        val activityScenario = ActivityScenario.launch(ListActivity::class.java)
        // At launch of the app check main screen is visible
        onView(withId(R.id.mainLayout)).check(matches(isDisplayed()))
    }

    @Test
    fun testIsSearchBoxVisible() {
        val activityScenario = ActivityScenario.launch(ListActivity::class.java)
        // test search view is visible and input performing
        onView(withId(R.id.userSearchView)).check(matches(isDisplayed()))
        val testInput = "search"
        onView(withId(R.id.userSearchView)).perform(replaceText(testInput))
        onView(withId(R.id.userSearchView)).check(matches(withText(testInput)))
    }

    @Test
    fun testIsFilterIconVisible() {
        val activityScenario = ActivityScenario.launch(ListActivity::class.java)

        // check list count is visible and click is performing
        onView(withId(R.id.ivListCount)).check(matches(isDisplayed()))
        onView(withId(R.id.ivListCount)).perform(click())
        onView(withId(R.id.clListCount)).check(matches(isDisplayed()))

        // Ensure input value is displayed in edittext
        val testInput = "10"
        onView(withId(R.id.etAddCount)).perform(replaceText(testInput))
        onView(withId(R.id.etAddCount)).check(matches(withText(testInput)))

        // check fetch button is visible and click is performing
        onView(withId(R.id.btnFetchList)).check(matches(isDisplayed()))
        onView(withId(R.id.btnFetchList)).perform(click())

        // after fetch click check main layout is visible
        onView(withId(R.id.mainLayout)).check(matches(isDisplayed()))

    }

    @Test
    fun testRecyclerViewIsDisplayedAndPerformItemClick() {
        // Launch the activity
        val activityScenario = ActivityScenario.launch(ListActivity::class.java)

        // Ensure RecyclerView is displayed
        onView(withId(R.id.userRecyclerView))
            .check(matches(isDisplayed()))

        // Wait for data to load and verify if the first item in the RecyclerView is displayed
        onView(withId(R.id.userRecyclerView))
            .perform(RecyclerViewActions.scrollToPosition<UserAdapter.UserViewHolder>(0))

        // Check if the item at position 0 is displayed and perform a click on it
        onView(withId(R.id.userRecyclerView))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<UserAdapter.UserViewHolder>(
                    0,
                    click()
                )
            )

        // After clicking, check if the detail screen is displayed
        onView(withId(R.id.llUserDetails))
            .check(matches(isDisplayed()))

        // verify the content in the detail screen (like name, address, etc.)
        onView(allOf(withId(R.id.userName), isDisplayed())).check(matches(isDisplayed()))
        onView(allOf(withId(R.id.userDob), isDisplayed())).check(matches(isDisplayed()))
        onView(allOf(withId(R.id.userEmail), isDisplayed())).check(matches(isDisplayed()))
        onView(allOf(withId(R.id.userPhone), isDisplayed())).check(matches(isDisplayed()))
        onView(allOf(withId(R.id.userAddress), isDisplayed())).check(matches(isDisplayed()))
        onView(allOf(withId(R.id.userGender), isDisplayed())).check(matches(isDisplayed()))
        onView(allOf(withId(R.id.userImage), isDisplayed())).check(matches(isDisplayed()))

    }

}