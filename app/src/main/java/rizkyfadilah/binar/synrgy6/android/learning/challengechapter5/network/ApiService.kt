package rizkyfadilah.binar.synrgy6.android.learning.challengechapter5.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("movie/popular")
    suspend fun getMovie(): Response<MovieResponse>

    @GET("movie/{movie_id}")
    suspend fun getDetailMovie(
        @Path("movie_id") movieId: Int
    ): Response<MovieResponse.Results>
}