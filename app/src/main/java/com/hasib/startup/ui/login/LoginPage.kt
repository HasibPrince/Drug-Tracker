package com.hasib.startup.ui.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.hasib.startup.ui.UIState
import timber.log.Timber

@Composable
fun LoginPage(
    innerPadding: PaddingValues,
    loginViewModel: LoginViewModel,
    onNavigationToMedicationList: () -> Unit
) {

    val loginState by loginViewModel.loginState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Create New Account",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 32.dp, top = 32.dp)
        )

        when (loginState) {
            is UIState.Loading -> {
                Timber.d("Sign up state: Loading")

                CircularProgressIndicator(
                    modifier = Modifier
                        .size(100.dp)
                        .padding(16.dp),
                    color = Color(0xFF007BFF)
                )
            }

            is UIState.Success -> {
                onNavigationToMedicationList()
            }

            is UIState.Error -> {
                HandleError(loginState, loginViewModel)
            }

            else -> {}
        }

        OutlinedTextField(
            value = loginViewModel.email.collectAsState().value,
            onValueChange = { loginViewModel.email.value = it },
            label = { Text("Email") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = loginViewModel.password.collectAsState().value,
            onValueChange = { loginViewModel.password.value = it },
            label = { Text("Create a password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                loginViewModel.signIn()
            },
            modifier = Modifier
                .padding(bottom = 32.dp)
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF007BFF) // Blue button
            )
        ) {
            Text("Log In", color = Color.White)
        }
    }
}

@Composable
private fun HandleError(
    loginState: UIState<String>,
    loginViewModel: LoginViewModel
) {
    val errorMessage = (loginState as UIState.Error).message
    AlertDialog(
        onDismissRequest = {
            loginViewModel.loginState.value = UIState.Idle
        },
        title = { Text("Error") },
        text = { Text(errorMessage) },
        confirmButton = {
            TextButton(onClick = {
                loginViewModel.loginState.value = UIState.Idle
            }) {
                Text("OK")
            }
        }
    )
}