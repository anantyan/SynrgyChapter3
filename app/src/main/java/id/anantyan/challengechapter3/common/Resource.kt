package id.anantyan.challengechapter3.common

sealed class Resource<T>(
    val data: T? = null
) {
    class Success<T>(data: T) : Resource<T>(data = data)
    class Loading<T> : Resource<T>()
}
