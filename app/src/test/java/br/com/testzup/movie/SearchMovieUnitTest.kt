package br.com.testzup.movie

import br.com.testzup.model.Movie
import br.com.testzup.network.model.BodyRequestMovies
import br.com.testzup.util.SnappyDBHelper
import com.snappydb.DB
import com.snappydb.SnappydbException
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.*
import org.mockito.Mockito.`when`
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchMovieUnitTest {

    @Mock
    lateinit var view: MovieContract.View

    @Mock
    lateinit var call: Call<BodyRequestMovies>

    lateinit var presenter: MoviePresenter

    @Mock
    lateinit var snappydb: DB
    @Mock
    lateinit var snappyDbHelper: SnappyDBHelper

    @Mock
    lateinit var snappydbBroken: DB
    @Mock
    lateinit var snappyDbBrokenHelper: SnappyDBHelper

    @Captor
    var callbackArgumentCaptor: ArgumentCaptor<Callback<BodyRequestMovies>>? = null
    private var bodyRequest: BodyRequestMovies? = null
    private var response: Response<BodyRequestMovies>? = null

    private var movies = ArrayList<Movie>()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        presenter = MoviePresenter()
        val movie = Movie()
        with(movie) {
            title = "Batman Returns"
            year = "1992"
            imdbID = "tt0103776"
            tipe = "movie"
            poster = "https://m.media-amazon.com/images/M/MV5BOGZmYzVkMmItM2NiOS00MDI3LWI4ZWQtMTg0YWZkODRkMmViXkEyXkFqcGdeQXVyODY0NzcxNw@@._V1_SX300.jpg"
        }

        movies.add(movie)
        snappyDbHelper = createMockSnappyDb()
        snappyDbBrokenHelper = createBrokenMockSnappyDb()
    }

    @Test
    fun showMovies() {
        bodyRequest = BodyRequestMovies(movies)
        response = Response.success(bodyRequest)

        callbackArgumentCaptor?.apply {
            presenter.listMovies(call, view)
            Mockito.verify(call).enqueue(capture())
            response?.apply {
                value?.onResponse(call, this)
            }

            Mockito.verify(view)?.setProgressMovie(false)
            response?.body()?.let {
                Mockito.verify(view)?.showMovies(it.search)
                Assert.assertEquals(it.search, movies)
            }
        }
    }

    @Test
    fun showErrorMovie() {
        val mediaType = MediaType.parse("application/json; charset=utf-8")
        val body = ResponseBody.create(mediaType, "Unauthorized")

        response = Response.error(401, body)
        presenter.listMovies(call, view)

        Mockito.verify(call)?.enqueue(callbackArgumentCaptor?.capture())
        callbackArgumentCaptor?.value?.onFailure(call, Throwable("Unauthorized"))

        Mockito.verify(view)?.setProgressMovie(false)
        Mockito.verify(view)?.showErrorMovie("Unauthorized")
    }

    @Test
    fun saveAndReadMovies() {
        val success = snappyDbHelper.saveMovie(movies)
        Assert.assertTrue(success)

        val myMovies = snappyDbHelper.getMovies()
        Assert.assertEquals(myMovies, movies)
    }

    @Test
    fun saveAndReadFaileMovies() {
        val success = snappyDbBrokenHelper.saveMovie(movies)
        Assert.assertTrue(success)

        val myMovies = snappyDbBrokenHelper.getMovies()
        Assert.assertNotEquals (myMovies, movies)
    }

    private fun createMockSnappyDb(): SnappyDBHelper {
        try {
            `when`(snappydb.findKeys(SnappyDBHelper.MOVIES)).thenReturn(arrayOfNulls(1))
            `when`(snappydb.getObject(SnappyDBHelper.MOVIES, ArrayList<Movie>().javaClass)).thenReturn(movies)
        } catch (e: SnappydbException) {
            e.printStackTrace()
        }
        return SnappyDBHelper(snappydb)
    }

    private fun createBrokenMockSnappyDb(): SnappyDBHelper {
        try {
            `when`(snappydbBroken.findKeys(SnappyDBHelper.MOVIES)).thenReturn(arrayOfNulls(0))
        } catch (e: SnappydbException) {
            e.printStackTrace()
        }
        return SnappyDBHelper(snappydbBroken)
    }
}

