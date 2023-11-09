package rizkyfadilah.binar.synrgy6.android.learning.challengechapter5.utils

sealed class LoadingResultHandler<T>(
    val data: T? = null
) {
    class Loading<T> : LoadingResultHandler<T>()
    class Content<T>(data: T?) : LoadingResultHandler<T>(data = data)
}
