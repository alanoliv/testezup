package br.com.testzup.movie

import android.support.test.espresso.Espresso
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.matcher.ViewMatchers.isRoot
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.view.KeyEvent
import br.com.testzup.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
open class SearchMovieScreenTest {

    @Rule
    @JvmField
    val activityTestRule = ActivityTestRule<SearchMovieActivity>(SearchMovieActivity::class.java)

    @Test
    fun clickSearchView() {
        onView(withId(R.id.searchViewMovies)).perform(typeText("batman"))
        Thread.sleep(1000)
        Espresso.closeSoftKeyboard()
        Thread.sleep(1000)
    }
}