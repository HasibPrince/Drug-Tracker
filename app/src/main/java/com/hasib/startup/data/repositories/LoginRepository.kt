package com.hasib.startup.data.repositories

import com.google.firebase.auth.FirebaseAuth
import com.hasib.startup.data.model.Result
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginRepository @Inject constructor() {

    suspend fun signUpWithEmail(email: String, password: String): Result<Boolean> {
        return try {
            FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(email, password)
                .await()
            Result.Success(true)
        } catch (e: Exception) {
            Timber.d("Sign up failed: ${e.message}")
            Result.Error(e)
        }
    }

    suspend fun signInWithEmail(email: String, password: String): Result<Boolean> {
        return try {
            FirebaseAuth.getInstance()
                .signInWithEmailAndPassword(email, password)
                .await()
            Result.Success(true)
        } catch (e: Exception) {
            Timber.d("Sign in failed: ${e.message}")
            Result.Error(e)
        }
    }
}