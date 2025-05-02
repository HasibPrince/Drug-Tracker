package com.hasib.startup.ui

sealed class UIState {
    object Idle : UIState()
    object Loading : UIState()
    data class Success(val email: String) : UIState()
    data class Error(val message: String) : UIState()
}