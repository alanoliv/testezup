package br.com.testzup.movie

import android.content.Intent
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import br.com.testzup.R
import br.com.testzup.model.Movie
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
open class MoviesScreenTest {

    @Rule
    @JvmField
    val mActivityRule: ActivityTestRule<MoviesActivity> =
        object : ActivityTestRule<MoviesActivity>(MoviesActivity::class.java) {
            override fun getActivityIntent(): Intent {

                val movies = ArrayList<Movie>()
                val movie = Movie()
                with(movie) {
                    title = "Batman Returns"
                    year = "1992"
                    imdbID = "tt0103776"
                    tipe = "movie"
                    poster = "https://m.media-amazon.com/images/M/MV5BOGZmYzVkMmItM2NiOS00MDI3LWI4ZWQtMTg0YWZkODRkMmViXkEyXkFqcGdeQXVyODY0NzcxNw@@._V1_SX300.jpg"
                }
                movies.add(movie)

                val targetContext = InstrumentationRegistry.getInstrumentation().targetContext
                val result = Intent(targetContext, MoviesActivity::class.java)
                result.putExtra("movies", movies)
                return result
            }
        }

    @Test
    fun clickOnItemRecyclerMovie() {
        Thread.sleep(2000)
        Espresso.onView(withId(R.id.recyclerViewMovies))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, ViewActions.click()))
        Thread.sleep(2000)
    }
}