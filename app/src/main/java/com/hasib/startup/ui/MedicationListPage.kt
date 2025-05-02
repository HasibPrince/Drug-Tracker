package com.hasib.startup.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MedicationListPage() {
    Column {
        Spacer(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        )
        Text(
            text = "Medication List Page",
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        )
    }
}