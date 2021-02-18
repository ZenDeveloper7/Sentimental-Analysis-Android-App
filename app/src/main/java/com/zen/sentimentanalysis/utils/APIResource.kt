package com.zen.sentimentanalysis.utils


sealed class APIResource<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : APIResource<T>(data)
    class Failure<T>(message: String, data: T? = null) : APIResource<T>(data, message)
    class Loading<T> : APIResource<T>()
}