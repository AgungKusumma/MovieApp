package rizkyfadilah.binar.synrgy6.android.learning.challengechapter5.utils

sealed class NetworkResult<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T?) : NetworkResult<T>(data = data)
    class Loading<T> : NetworkResult<T>()
    class Error<T>(message: String?) :
        NetworkResult<T>(data = null, message = message)
}
