package com.hasib.startup.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.hilt.navigation.compose.hiltViewModel
import com.hasib.startup.ui.login.LoginPage
import com.hasib.startup.ui.login.LoginViewModel
import com.hasib.startup.ui.signup.CreateAccountScreen
import com.hasib.startup.ui.signup.SignupViewModel
import kotlinx.serialization.Serializable

@Serializable
object Landing

@Serializable
object Login

@Serializable
object Signup

@Serializable
object MedicationList

@Composable
fun AppContainer(innerPadding: PaddingValues) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Landing) {
        composable<Landing> {
            OnboardingScreen(innerPadding, {
                navController.navigate(Login)
            }, {
                navController.navigate(Signup)
            })
        }

        composable<Login> {
            val loginViewModel: LoginViewModel = hiltViewModel()
            LoginPage(innerPadding, loginViewModel) {
                navController.navigate(MedicationList)
            }
        }

        composable<Signup> {
            val signupViewModel: SignupViewModel = hiltViewModel()
            CreateAccountScreen(innerPadding, signupViewModel) {
                navController.navigate(MedicationList)
            }
        }

        composable<MedicationList> {
            MedicationListPage()
        }
    }
}