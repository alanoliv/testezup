package br.com.testzup.movie

import br.com.testzup.ApplicationZup
import br.com.testzup.R
import br.com.testzup.model.Movie
import br.com.testzup.network.model.BodyRequestMovies
import br.com.testzup.util.SnappyDBHelper
import com.snappydb.DB
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoviePresenter : MovieContract.MovieActionListener {

    override fun listMovies(call: Call<BodyRequestMovies>, view: MovieContract.View) {
        view.setProgressMovie(true)
        call.enqueue(object : Callback<BodyRequestMovies> {

            override fun onResponse(call: Call<BodyRequestMovies>, response: Response<BodyRequestMovies>) {
                view.setProgressMovie(false)
                if (response.isSuccessful) {
                    response.body()?.apply {
                        if (search.isNullOrEmpty()) {
                            view.showEmpytMovie(ApplicationZup.instance.getString(R.string.empty_list))
                            return
                        }

                        if (search.size > 0) {
                            view.showMovies(search)
                        } else {
                            view.showEmpytMovie(ApplicationZup.instance.getString(R.string.empty_list))
                        }
                    } ?: run {
                        view.showEmpytMovie(ApplicationZup.instance.getString(R.string.empty_list))
                    }
                } else {
                    view.showEmpytMovie(ApplicationZup.instance.getString(R.string.error_default))
                }
            }

            override fun onFailure(call: Call<BodyRequestMovies>, t: Throwable) {
                view.setProgressMovie(false)
                t.message?.apply {
                    view.showErrorMovie(this)
                }
            }
        })
    }

    override fun addMovie(snappyDB: DB, movies: ArrayList<Movie>) : Boolean {
        val snappyDbHelper = SnappyDBHelper(snappyDB)
        return snappyDbHelper.saveMovie(movies)
    }

    override fun getMovies(snappyDB: DB): ArrayList<Movie>? {
        val snappyDbHelper = SnappyDBHelper(snappyDB)
        return snappyDbHelper.getMovies()
    }
}