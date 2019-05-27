package br.com.testzup.movie

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore.Video.VideoColumns.CATEGORY
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import br.com.testzup.R
import br.com.testzup.model.Movie
import br.com.testzup.movie.MovieDetailActivity.Companion.MOVIE
import kotlinx.android.synthetic.main.activity_movie_detail.*
import kotlinx.android.synthetic.main.activity_movies.*
import kotlinx.android.synthetic.main.activity_movies.toolbar

class MoviesActivity : AppCompatActivity() {

    private var movies : ArrayList<Movie>? = null

    companion object {
        const val MOVIES = "movies"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies)
        getDataExtra(intent.extras)

    }

    private fun getDataExtra(extras: Bundle?) {
        movies = extras?.getSerializable(MOVIES) as ArrayList<Movie>
        bindMovies()
        setToolbar()
    }

    private fun setToolbar(){
        toolbar.title = getString(R.string.related)
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24px)
        setSupportActionBar(toolbar)
    }

    private fun bindMovies() {
        movies?.let {
            recyclerViewMovies?.adapter = MovieAdapter(it) { position ->
                val movie = it[position]
                val intent = Intent(this@MoviesActivity, MovieDetailActivity::class.java)
                intent.putExtra(MOVIE, movie)
                startActivity(intent)
            }
            val layoutManager = LinearLayoutManager(this)
            recyclerViewMovies?.layoutManager = layoutManager
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}
