package br.com.testzup.network.model

import br.com.testzup.model.Movie
import com.google.gson.annotations.SerializedName

class BodyRequestMovies (
    @SerializedName("Search") var search: ArrayList<Movie>
)