package br.com.testzup.movie

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import br.com.testzup.R
import br.com.testzup.model.Movie
import br.com.testzup.util.SnappyDBHelper
import com.snappydb.DBFactory
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_movie_detail.*
import kotlinx.android.synthetic.main.activity_movies.toolbar
import kotlinx.android.synthetic.main.partial_movie_detail.*
import kotlinx.android.synthetic.main.partial_movie_detail.textViewYear

class MovieDetailActivity : AppCompatActivity() {

    companion object {
        const val MOVIE = "movie"
    }

    private var movie : Movie? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        getDataExtra(intent.extras)
        buttonSaveMovie()
    }

    private fun getDataExtra(extras: Bundle?) {
        movie = extras?.getSerializable(MOVIE) as Movie
        movie?.apply {
            bindMovie()
            setToolbar()
        }
    }

    private fun setToolbar(){
        toolbar.title = movie?.title
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24px)
        setSupportActionBar(toolbar)

        toolbar_layout.apply {
            setCollapsedTitleTextColor(Color.WHITE)
            setExpandedTitleColor(Color.WHITE)
        }
    }

    private fun bindMovie() {
        Picasso.get()
            .load(movie?.poster)
            .into(imageViewMovieDesc)

        textViewImdbID.text = movie?.imdbID
        textViewYear.text = movie?.year
        textViewType.text = movie?.tipe
    }

    private fun buttonSaveMovie() {
        floatingButtonSalve.setOnClickListener {
            floatingButtonSalve.isEnabled = false
            saveMovie()
        }
    }

    private fun saveMovie() {
        val presenter = MoviePresenter()

        movie?.apply {
            var listMovies = ArrayList<Movie>()
            presenter.getMovies(DBFactory.open(this@MovieDetailActivity, SnappyDBHelper.ZUP))?.apply {
                listMovies = this
            }
            listMovies.add(this)

            if (presenter.addMovie(DBFactory.open(this@MovieDetailActivity, SnappyDBHelper.ZUP), listMovies)){
                Toast.makeText(this@MovieDetailActivity, getString(R.string.save_success), Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this@MovieDetailActivity, getString(R.string.error_default), Toast.LENGTH_LONG).show()
            }
            finish()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}
