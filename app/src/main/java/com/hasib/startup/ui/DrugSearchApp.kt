package com.hasib.startup.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable

@Serializable
object Landing

@Serializable
object Login

@Serializable
object Signup

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
            LoginPage(innerPadding)
        }

        composable<Signup> {
            CreateAccountScreen(innerPadding)
        }
    }
}