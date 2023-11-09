package rizkyfadilah.binar.synrgy6.android.learning.challengechapter5.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfig {
    private const val BASE_URL = "https://api.themoviedb.org/3/"
    const val BASE_URL_IMG = "https://image.tmdb.org/t/p/w500"
    private const val BEARER_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJlZTY2YzVhNjg1MWEyOGNmOWU2OWQ5MDljM2MwOTMxYyIsInN1YiI6IjY1NDdhYjlmMWFjMjkyN2IzMDI4MjlkNCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.ctHx086RzvB4OD0n-1zOQqhDA_RA9Zi4YgYzJjqz8wM"

    private val logging: HttpLoggingInterceptor
        get() {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            return httpLoggingInterceptor.apply {
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            }
        }

    private val interceptor = Interceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $BEARER_TOKEN")
                .build()
            chain.proceed(request)
        }

    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .addInterceptor(interceptor)
        .build()

    val instanceAPI: ApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        retrofit.create(ApiService::class.java)
    }
}