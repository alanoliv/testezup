package br.com.testzup.movie

import br.com.testzup.model.Movie
import br.com.testzup.network.model.BodyRequestMovies
import com.snappydb.DB
import retrofit2.Call

interface MovieContract {

    interface View {
        fun setProgressMovie(active: Boolean)

        fun showMovies(movies: ArrayList<Movie>)

        fun showErrorMovie(message : String)

        fun showEmpytMovie(message : String)
    }

    interface MovieActionListener {
        fun listMovies(call: Call<BodyRequestMovies>, view: View)
        fun addMovie(snappyDB: DB, movies: ArrayList<Movie>) : Boolean
        fun getMovies(snappyDB: DB) : ArrayList<Movie>?
    }
}