package com.hasib.startup.ui.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hasib.startup.data.repositories.LoginRepository
import com.hasib.startup.data.model.getErrorMessage
import com.hasib.startup.data.model.isSuccess
import com.hasib.startup.ui.ResourceProvider
import com.hasib.startup.ui.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.hasib.startup.R

@HiltViewModel
class SignupViewModel @Inject constructor(private val loginRepository: LoginRepository) : ViewModel() {
    val name = MutableStateFlow("")
    val email = MutableStateFlow("")
    val password = MutableStateFlow("")


    val signupState = MutableStateFlow<UIState<String>>(UIState.Idle)

    fun signUp() {
        viewModelScope.launch {
            signupState.value = UIState.Loading
            delay(3000)
            val result = loginRepository.signUpWithEmail(email.value, password.value)
            if (result.isSuccess()) {
                performLogin()
            } else {
                signupState.value = UIState.Error(result.getErrorMessage() ?: ResourceProvider.getString(R.string.unknownError))
            }
        }
    }

    private suspend fun performLogin() {
        val result = loginRepository.signInWithEmail(email.value, password.value)
        if (result.isSuccess()) {
            signupState.value = UIState.Success(email.value)
        } else {
            signupState.value = UIState.Error(result.getErrorMessage() ?: ResourceProvider.getString(R.string.unknownError))
        }
    }
}