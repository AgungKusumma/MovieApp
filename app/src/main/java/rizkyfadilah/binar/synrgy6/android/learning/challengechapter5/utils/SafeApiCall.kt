package rizkyfadilah.binar.synrgy6.android.learning.challengechapter5.utils

import retrofit2.Response

abstract class SafeApiCall {
    suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): NetworkResult<T> {
        try {
            val response: Response<T> = apiCall()
            if (response.isSuccessful) {
                response.body()?.let {
                    return NetworkResult.Success(data = it)
                }
            }
            return error(errorMessage = "${response.code()} : ${response.message()}")
        } catch (e: Exception) {
            return error(errorMessage = e.message ?: e.toString())
        }
    }

    private fun <T> error(errorMessage: String): NetworkResult<T> {
        return NetworkResult.Error(message = errorMessage)
    }
}