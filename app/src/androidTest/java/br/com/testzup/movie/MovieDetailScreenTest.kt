package br.com.testzup.movie

import android.content.Intent
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import br.com.testzup.R
import br.com.testzup.model.Movie
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
open class MovieDetailScreenTest {
    @Rule
    @JvmField
    val mActivityRule: ActivityTestRule<MovieDetailActivity> =
        object : ActivityTestRule<MovieDetailActivity>(MovieDetailActivity::class.java) {
            override fun getActivityIntent(): Intent {

                val movie = Movie()
                with(movie) {
                    title = "Batman Returns"
                    year = "1992"
                    imdbID = "tt0103776"
                    tipe = "movie"
                    poster = "https://m.media-amazon.com/images/M/MV5BOGZmYzVkMmItM2NiOS00MDI3LWI4ZWQtMTg0YWZkODRkMmViXkEyXkFqcGdeQXVyODY0NzcxNw@@._V1_SX300.jpg"
                }

                val targetContext = InstrumentationRegistry.getInstrumentation().targetContext
                val result = Intent(targetContext, MovieDetailActivity::class.java)
                result.putExtra("movie", movie)
                return result
            }
        }


    @Test
    fun clickOnItemSalveMovie() {
        Espresso.onView(ViewMatchers.withId(R.id.floatingButtonSalve)).perform(ViewActions.click())
        Thread.sleep(2000)
    }
}