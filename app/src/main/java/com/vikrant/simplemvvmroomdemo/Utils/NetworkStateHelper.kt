package com.vikrant.simplemvvmroomdemo.Utils

data class NetworkStateHelper<out T>(val status: Status, val data: T?, val msg: String?) {
    companion object {
        fun <T> success(data: T?): NetworkStateHelper<T> {
            return NetworkStateHelper(Status.SUCCESS, data, null)
        }

        fun <T> error(message: String, data: T? = null): NetworkStateHelper<T> {
            return NetworkStateHelper(Status.ERROR, data, message)
        }

        fun <T> loading(data: T? = null): NetworkStateHelper<T> {
            return NetworkStateHelper(Status.LOADING, data, null)
        }
    }
}

enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}