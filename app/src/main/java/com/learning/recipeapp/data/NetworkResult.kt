package com.learning.recipeapp.data

sealed class NetworkResult<T>(val data: T? = null, val message: String? = null) {
    sealed class Success<T>(data: T) : NetworkResult<T>(data)
    sealed class Error<T>(error: String?, data: T? = null) : NetworkResult<T>(data, error)
    sealed class Loading<T> : NetworkResult<T>()
}