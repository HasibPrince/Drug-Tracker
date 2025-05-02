package com.hasib.startup.data.api

import com.hasib.startup.domian.model.Result
import kotlinx.coroutines.CancellationException
import retrofit2.HttpException
import retrofit2.Response

suspend fun <T> handleDataFetch(apiCall: suspend () -> Response<T>): Result<T> {
    return try {
        val response = apiCall()
        val body = response.body()
        if (response.isSuccessful && body != null) {
            Result.Success(body)
        } else {
            Result.Error(Exception("Error: ${response.code()} ${response.message()}"))
        }
    } catch (e: HttpException) {
        Result.Error(e)
    } catch (e: Throwable) {
        if (e is CancellationException) {
            throw e
        }
        Result.Error(e)
    }
}
