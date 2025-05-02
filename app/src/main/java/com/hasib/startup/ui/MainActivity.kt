package com.hasib.startup.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hasib.startup.theme.StartupTheme
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("onCreate: MainActivity")
        setContent {
            StartupTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppContainer(
                        innerPadding = PaddingValues(16.dp),
                    )
                }
            }
        }
    }
}