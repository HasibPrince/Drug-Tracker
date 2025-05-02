package com.hasib.startup.ui

sealed class UIState<out T> {
    object Idle : UIState<Nothing>()
    object Loading : UIState<Nothing>()
    data class Success<out T>(val data: T) : UIState<T>()
    data class Error<out T>(val message: String) : UIState<T>()
}