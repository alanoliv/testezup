package br.com.testzup.network

import br.com.testzup.ApplicationZup
import br.com.testzup.R
import br.com.testzup.network.model.BodyRequestMovies
import retrofit2.Call

class OmdbServiceEndPoint : RestClient() {

    fun getMovies(title : String) : Call<BodyRequestMovies> {
        val service = OmdbServiceEndPoint().createService(OmdbService::class.java)
        return service.getMovies(ApplicationZup.instance.getString(R.string.omdb_api_key), title)
    }
}