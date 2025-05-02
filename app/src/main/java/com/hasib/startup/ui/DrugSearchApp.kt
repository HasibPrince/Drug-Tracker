package com.hasib.startup.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.firebase.auth.FirebaseAuth
import com.hasib.startup.ui.landing.OnboardingScreen
import com.hasib.startup.ui.login.LoginPage
import com.hasib.startup.ui.login.LoginViewModel
import com.hasib.startup.ui.medicationList.MedicationListPage
import com.hasib.startup.ui.medicationSearch.SearchMedicationScreen
import com.hasib.startup.ui.medicationSearch.SearchMedicationViewModel
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

@Serializable
object MedicationSearch

@Composable
fun AppContainer(innerPadding: PaddingValues) {
    val navController = rememberNavController()
    val startDestination = if (FirebaseAuth.getInstance().currentUser != null) {
        MedicationList
    } else {
        Landing
    }

    NavHost(navController = navController, startDestination = startDestination) {
        composable<Landing> {
            OnboardingScreen(innerPadding, {
                navController.navigate(Login)
            }, {
                navController.navigate(Signup)
            }, {
                navController.navigate(MedicationList) {
                    popUpTo(Landing) {
                        inclusive = true
                    }
                }
            })
        }

        composable<Login> {
            val loginViewModel: LoginViewModel = hiltViewModel()
            LoginPage(innerPadding, loginViewModel) {
                navController.navigate(MedicationList) {
                    popUpTo(Landing) {
                        inclusive = true
                    }
                }
            }
        }

        composable<Signup> {
            val signupViewModel: SignupViewModel = hiltViewModel()
            CreateAccountScreen(innerPadding, signupViewModel) {
                navController.navigate(MedicationList) {
                    popUpTo(Landing) {
                        inclusive = true
                    }
                }
            }
        }

        composable<MedicationList> {
            MedicationListPage{
                navController.navigate(MedicationSearch)
            }
        }

        composable<MedicationSearch> {
            val viewModel: SearchMedicationViewModel = hiltViewModel()
            SearchMedicationScreen(viewModel) {
                navController.navigateUp()
            }
        }
    }
}