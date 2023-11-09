package rizkyfadilah.binar.synrgy6.android.learning.challengechapter5.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("movie/top_rated")
    suspend fun getMovie(
        @Query("page") page: Int = 10
    ): Response<MovieResponse>

    @GET("movie/{movie_id}")
    suspend fun getDetailMovie(
        @Path("movie_id") movieId: Int
    ): Response<DetailMovieResponse>
}