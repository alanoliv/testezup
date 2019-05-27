package br.com.testzup.network

import br.com.testzup.network.model.BodyRequestMovies
import retrofit2.Call
import retrofit2.http.*

interface OmdbService  {
    @GET(".")
    fun getMovies(@Query("apikey") apikey: String, @Query("s") title: String): Call<BodyRequestMovies>
}