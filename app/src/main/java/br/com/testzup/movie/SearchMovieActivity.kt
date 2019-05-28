package br.com.testzup.movie

import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.SearchView
import br.com.testzup.R
import br.com.testzup.model.Movie
import br.com.testzup.movie.MoviesActivity.Companion.MOVIES
import br.com.testzup.network.OmdbServiceEndPoint
import br.com.testzup.util.SnappyDBHelper
import com.snappydb.DBFactory
import kotlinx.android.synthetic.main.activity_search_movie.*
import kotlinx.android.synthetic.main.activity_search_movie.toolbar

class SearchMovieActivity : AppCompatActivity(), MovieContract.View {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_movie)

        searchViewMoviFocus()
        setToolbar()

        searchViewMovies.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()) {
                    loadMovies(query.trim())
                    searchViewMovies.setQuery("", false)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    override fun onResume() {
        super.onResume()
        bindMyMovies()
    }

    private fun bindMyMovies() {
        val presenter = MoviePresenter()
        presenter.getMovies(DBFactory.open(this@SearchMovieActivity, SnappyDBHelper.ZUP))?.apply {
            recyclerViewMyMovie?.adapter = MovieAdapter(this.asReversed()) {
            }
            val layoutManager = LinearLayoutManager(this@SearchMovieActivity)
            recyclerViewMyMovie?.layoutManager = layoutManager
            textViewEmpytMyMovie.visibility = View.GONE
        } ?: run {
            textViewEmpytMyMovie.visibility = View.VISIBLE
        }
    }

    private fun setToolbar(){
        toolbar.title = getString(R.string.zup_movies)
        setSupportActionBar(toolbar)
    }


    private fun searchViewMoviFocus() {
        searchViewMovies.setIconifiedByDefault(true)
        searchViewMovies.isFocusable = true
        searchViewMovies.isIconified = false
        searchViewMovies.requestFocusFromTouch()
    }

    private fun loadMovies(title : String) {
        val call = OmdbServiceEndPoint().getMovies(title)
        val presenter = MoviePresenter()
        presenter.listMovies(call, this)
    }

    override fun setProgressMovie(active: Boolean) {
        progressBarSearch.visibility = if (active) View.VISIBLE else View.GONE
    }

    override fun showMovies(movies: ArrayList<Movie>) {
        val intent = Intent(this@SearchMovieActivity, MoviesActivity::class.java)
        intent.putExtra(MOVIES, movies)
        startActivity(intent)
    }

    override fun showErrorMovie(message: String) {
        showErrorMessages(message)
    }

    override fun showEmpytMovie(message: String) {
        showErrorMessages(message)
    }

    private fun showErrorMessages(errorMessage : String) {
        val snackBarError = Snackbar.make(view, errorMessage, Snackbar.LENGTH_INDEFINITE)
        snackBarError.setAction(getString(R.string.ok)) { snackBarError.dismiss() }
        snackBarError.setActionTextColor(Color.WHITE)
        snackBarError.show()
    }
}
