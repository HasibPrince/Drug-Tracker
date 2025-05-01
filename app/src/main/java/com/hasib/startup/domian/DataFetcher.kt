package com.hasib.startup.domian

import com.hasib.startup.domian.model.Result
import kotlinx.coroutines.CancellationException

suspend fun <T> handleDataFetch(apiCall: suspend () -> T): Result<T> {
    return try {
        val response = apiCall()
        Result.Success(response)
    } catch (e: Throwable) {
        if (e is CancellationException) {
            throw e
        }
        Result.Error(e)
    }
}
