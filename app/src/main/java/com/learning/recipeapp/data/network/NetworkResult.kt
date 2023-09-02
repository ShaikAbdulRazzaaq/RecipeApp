package com.learning.recipeapp.data.network

sealed class NetworkResult<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : NetworkResult<T>(data)
    class Error<T>(error: String?, data: T? = null) : NetworkResult<T>(data, error)
    class Loading<T> : NetworkResult<T>()
}