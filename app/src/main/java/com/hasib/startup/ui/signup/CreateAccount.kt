package com.hasib.startup.ui.signup

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.hasib.startup.ui.UIState
import timber.log.Timber

@Composable
fun CreateAccountScreen(
    innerPadding: PaddingValues,
    signupViewModel: SignupViewModel,
    onNavigationToMedicationList: () -> Unit
) {

    val signUpState by signupViewModel.signupState.collectAsState()

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

        when (signUpState) {
            is UIState.Loading -> {
                Timber.d("Sign up state: Loading")

                CircularProgressIndicator(
                    modifier = Modifier
                        .size(100.dp)
                        .padding(16.dp),
                    color = Color(0xFF007BFF) // Blue color
                )
            }

            is UIState.Success -> {
                onNavigationToMedicationList()
            }

            is UIState.Error -> {
                HandleError(signUpState, signupViewModel)
            }

            else -> {}
        }

        OutlinedTextField(
            value = signupViewModel.name.collectAsState().value,
            onValueChange = { signupViewModel.name.value = it },
            label = { Text("Name") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = signupViewModel.email.collectAsState().value,
            onValueChange = { signupViewModel.email.value = it },
            label = { Text("Email") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = signupViewModel.password.collectAsState().value,
            onValueChange = { signupViewModel.password.value = it },
            label = { Text("Create a password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                signupViewModel.signUp()
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
            Text("Create New Account", color = Color.White)
        }
    }
}

@Composable
private fun HandleError(
    signUpState: UIState<String>,
    signupViewModel: SignupViewModel
) {
    val errorMessage = (signUpState as UIState.Error).message
    AlertDialog(
        onDismissRequest = {
            signupViewModel.signupState.value = UIState.Idle
        },
        title = { Text("Error") },
        text = { Text(errorMessage) },
        confirmButton = {
            TextButton(onClick = {
                signupViewModel.signupState.value = UIState.Idle
            }) {
                Text("OK")
            }
        }
    )
}