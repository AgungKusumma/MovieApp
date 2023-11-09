package rizkyfadilah.binar.synrgy6.android.learning.challengechapter5.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import rizkyfadilah.binar.synrgy6.android.learning.challengechapter5.utils.NetworkResult
import rizkyfadilah.binar.synrgy6.android.learning.challengechapter5.utils.SafeApiCall

class MovieRepository(private val apiService: ApiService) : SafeApiCall() {
    fun getMovie(): Flow<NetworkResult<MovieResponse>> {
        return flow {
            emit(NetworkResult.Loading())
            emit(safeApiCall {
                apiService.getMovie()
            })
        }.flowOn(Dispatchers.IO)
    }

    fun getDetailMovie(movieId: Int): Flow<NetworkResult<DetailMovieResponse>> {
        return flow {
            emit(NetworkResult.Loading())
            emit(safeApiCall {
                apiService.getDetailMovie(movieId)
            })
        }.flowOn(Dispatchers.IO)
    }
}