package com.hasib.startup.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hasib.startup.data.repositories.LoginRepository
import com.hasib.startup.data.model.getErrorMessage
import com.hasib.startup.data.model.isSuccess
import com.hasib.startup.ui.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.hasib.startup.R
import com.hasib.startup.ui.ResourceProvider


@HiltViewModel
class LoginViewModel @Inject constructor(private val loginRepository: LoginRepository) :
    ViewModel() {
    val email = MutableStateFlow("")
    val password = MutableStateFlow("")

    val loginState = MutableStateFlow<UIState<String>>(UIState.Idle)

    fun signIn() {
        viewModelScope.launch {
            loginState.value = UIState.Loading

            val result = loginRepository.signInWithEmail(email.value, password.value)
            if (result.isSuccess()) {
                loginState.value = UIState.Success(email.value)
            } else {
                loginState.value = UIState.Error(
                    result.getErrorMessage() ?: ResourceProvider.getString(R.string.unknownError)
                )
            }
        }
    }
}
