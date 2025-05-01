package com.hasib.startup.ui

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import androidx.annotation.RequiresPermission
import androidx.compose.runtime.mutableStateOf
import com.google.android.gms.location.CurrentLocationRequest
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import timber.log.Timber
import java.util.jar.Manifest

class LocationState(private val context: Context) {
    val locationState = mutableStateOf<Location?>(null)
    val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

    @SuppressLint("MissingPermission")
    fun getLocation() {
        val request = CurrentLocationRequest.Builder()
            .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
            .setMaxUpdateAgeMillis(10000)
            .build()

        fusedLocationProviderClient.getCurrentLocation(
            request, null
        ).addOnSuccessListener { location ->
            Timber.d("Location found: $location")
            locationState.value = location
        }
    }
}