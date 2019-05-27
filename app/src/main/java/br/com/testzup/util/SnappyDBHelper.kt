package br.com.testzup.util

import br.com.testzup.model.Movie
import com.snappydb.DB
import com.snappydb.SnappydbException

open class SnappyDBHelper(var snappydb: DB) {

    companion object {
        const val MOVIES = "movies"
        const val ZUP = "zup"
    }

    fun saveMovie(movies: ArrayList<Movie>): Boolean {
        var sucess = false
        try {
            snappydb.put(MOVIES, movies)
            snappydb.close()
            sucess = true
        } catch (e: SnappydbException) {
            e.printStackTrace()
        }

        return sucess
    }

    fun getMovies(): ArrayList<Movie>? {
        var movies: ArrayList<Movie>? = null
        try {
            val movieKey = snappydb.findKeys(MOVIES)
            if (movieKey.isNotEmpty()) {
                movies = snappydb.getObject(MOVIES, ArrayList<Movie>().javaClass)
            }
            snappydb.close()
        } catch (e: SnappydbException) {
            e.printStackTrace()
        }

        return movies
    }
}