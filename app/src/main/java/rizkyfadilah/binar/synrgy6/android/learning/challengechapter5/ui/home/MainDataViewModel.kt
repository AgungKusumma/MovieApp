package rizkyfadilah.binar.synrgy6.android.learning.challengechapter5.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import rizkyfadilah.binar.synrgy6.android.learning.challengechapter5.network.MovieRepository
import rizkyfadilah.binar.synrgy6.android.learning.challengechapter5.network.MovieResponse
import rizkyfadilah.binar.synrgy6.android.learning.challengechapter5.utils.NetworkResult

class MainDataViewModel(private val movieRepository: MovieRepository) : ViewModel() {
    val dataMovie: LiveData<NetworkResult<MovieResponse>> = movieRepository.getMovie().asLiveData()

    fun detailDataMovie(movieId: Int): LiveData<NetworkResult<MovieResponse.Results>> =
        movieRepository.getDetailMovie(movieId).asLiveData()

    class Factory(private val movieRepository: MovieRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainDataViewModel::class.java)) {
                return MainDataViewModel(movieRepository) as T
            } else {
                throw RuntimeException("No Assignable ViewModel")
            }
        }
    }
}