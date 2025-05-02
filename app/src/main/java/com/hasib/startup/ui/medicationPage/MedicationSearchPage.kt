package com.hasib.startup.ui.medicationPage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hasib.startup.R
import com.hasib.startup.data.model.ConceptProperty
import com.hasib.startup.ui.UIState
import com.hasib.startup.ui.AppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicationSearchPage(
    viewModel: SearchMedicationViewModel,
    onNavigateToDetailsPage: (conceptProperty: ConceptProperty) -> Unit = {},
    onBack: () -> Unit = {}
) {
    val searchQuery by viewModel.searchQuery.collectAsState()
    val filteredResults by viewModel.filteredMedications.collectAsState()

    Scaffold(
        topBar = {
            AppBar(onBack)
        },
        containerColor = Color(0xFFF5F5F9)
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            SearchBar(searchQuery, viewModel)

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                "Search Results",
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            when (filteredResults) {
                is UIState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        color = Color(0xFF007BFF)
                    )
                }

                is UIState.Error -> {
                    Text(
                        (filteredResults as UIState.Error).message,
                        style = TextStyle(
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center
                        ),
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }

                is UIState.Success -> {
                    val data = (filteredResults as UIState.Success).data
                    LazyColumn {
                        items(data.size) { item ->
                            val conceptProperty = data[item]
                            MedicationItem(conceptProperty = conceptProperty, onNavigateToDetailsPage)
                            Divider()
                        }
                    }
                }

                UIState.Idle -> {}
            }

        }
    }
}

@Composable
private fun SearchBar(
    searchQuery: String,
    viewModel: SearchMedicationViewModel
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFE5E5EA), shape = RoundedCornerShape(12.dp))
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                tint = Color.Gray,
                modifier = Modifier.size(20.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Box(
                modifier = Modifier
                    .weight(1f)
            ) {
                if (searchQuery.isEmpty()) {
                    Text(
                        text = "Search Medication",
                        color = Color.Gray,
                        fontSize = 16.sp
                    )
                }
                BasicTextField(
                    value = searchQuery,
                    onValueChange = viewModel::onSearchQueryChanged,
                    singleLine = true,
                    textStyle = LocalTextStyle.current.copy(fontSize = 16.sp),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            if (searchQuery.isNotEmpty()) {
                Box(modifier = Modifier
                    .padding(start = 8.dp)
                    .clickable(
                        onClick = { viewModel.onSearchQueryChanged("") }
                    )) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Clear",
                        tint = Color.Gray,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun MedicationItem(conceptProperty: ConceptProperty, onNavigateToDetailsPage: (ConceptProperty) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(vertical = 12.dp, horizontal = 12.dp)
            .clickable { onNavigateToDetailsPage(conceptProperty) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(R.drawable.ic_logo), // Replace with actual icon
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