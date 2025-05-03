package com.hasib.startup.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hasib.startup.R
import com.hasib.startup.data.model.ConceptProperty

@Composable
fun AppBar(onBack: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF5F5F9))
    ) {
        Row(modifier = Modifier
            .height(56.dp)
            .padding(top = 2.dp)
            .clickable { onBack() }) {
            Icon(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .align(Alignment.CenterVertically)
                    .size(18.dp),
                painter = painterResource(R.drawable.ic_back),
                contentDescription = "Back",
                tint = Color(0xFF007AFF)
            )
            Text(
                "Back",
                color = Color(0xFF007AFF),
                modifier = Modifier
                    .padding(start = 4.dp, end = 16.dp)
                    .align(Alignment.CenterVertically)
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = "Search Medication",
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp,
            color = Color.Black,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(top = 4.dp)
        )
    }
}

@Composable
fun MedicationItem(
    conceptProperty: ConceptProperty,
    onNavigateToDetailsPage: (ConceptProperty) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF5F5F9))
            .clip(RoundedCornerShape(8.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(vertical = 12.dp, horizontal = 12.dp)
                .clickable { onNavigateToDetailsPage(conceptProperty) },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.ic_capsule), // Replace with actual icon
                contentDescription = null,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(conceptProperty.name, modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = "RxCUI: ${conceptProperty.rxcui}",
                fontSize = 12.sp,
                color = Color.Gray
            )
            Icon(Icons.Default.KeyboardArrowRight, contentDescription = null)
        }
    }
}
