package com.gielow.cleanbaseapp.commons.network

sealed class Result<out T> {
    class Success<T>(val data: T) : Result<T>()
    class Failure(val error: String) : Result<Nothing>()
}