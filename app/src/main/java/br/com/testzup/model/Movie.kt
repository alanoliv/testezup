package br.com.testzup.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Movie : Serializable {
    @SerializedName("Title") var title: String = ""
    @SerializedName("Year") var year: String = ""
    @SerializedName("imdbID") var imdbID: String = ""
    @SerializedName("Type") var tipe: String = ""
    @SerializedName("Poster") var poster: String = ""
}