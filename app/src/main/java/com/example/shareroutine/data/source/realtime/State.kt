package com.example.shareroutine.data.source.realtime

sealed class Result<T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Failed<T>(val message: String) : Result<T>()

    companion object {
        fun <T> success(data: T) = Success(data)
        fun <T> failed(message: String) = Failed<T>(message)
    }
}
