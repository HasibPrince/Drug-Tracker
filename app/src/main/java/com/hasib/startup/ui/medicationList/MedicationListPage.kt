package com.hasib.startup.ui.medicationList

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hasib.startup.ui.MedicationItem
import com.hasib.startup.ui.UIState
import com.hasib.startup.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicationListPage(
    medicationListViewModel: MedicationListViewModel,
    onNavigationToMedicationSearch: () -> Unit,
    onNavigationToLanding: () -> Unit,
) {
    val medicationListState by medicationListViewModel.usersMedicationsStateFlow.collectAsState()

    LaunchedEffect(null) {
        medicationListViewModel.getUserMedications()
    }

    Scaffold(
        containerColor = Color(0xFFF5F5F9),
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                TextButton(
                    onClick = { onNavigationToMedicationSearch() },
                ) {
                    Icon(
                        painterResource(R.drawable.ic_plus),
                        contentDescription = null,
                        tint = Color(0xFF007BFF),
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Search Medications", color = Color(0xFF007BFF))
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            Row {
                Text(
                    text = "My Medications",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(vertical = 16.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "Logout",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    style = TextStyle(
                        color = Color(0xFF007BFF),
                        textAlign = TextAlign.End
                    ),
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .clickable {
                            medicationListViewModel.logout()
                            onNavigationToLanding()
                        },
                )
            }

            when (medicationListState) {
                is UIState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        color = Color(0xFF007BFF)
                    )
                }

                is UIState.Error -> {
                    Spacer(modifier = Modifier.weight(.5f))
                    Text(
                        (medicationListState as UIState.Error).message,
                        style = TextStyle(
                            fontSize = 18.sp,
                            textAlign = TextAlign.Center
                        ),
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Spacer(modifier = Modifier.weight(.5f))
                }

                is UIState.Success -> {
                    val medications = (medicationListState as UIState.Success).data
                    if (medications.isEmpty()) {
                        Text("No medications found", fontSize = 16.sp)
                    } else {
                        LazyColumn {
                            items(
                                medications.size,
                                key = { item -> medications[item].rxcui }) { item ->
                                val medicine = medications[item]
                                val context = LocalContext.current
                                val dismissState = rememberSwipeToDismissBoxState(
                                    confirmValueChange = {
                                        when (it) {
                                            SwipeToDismissBoxValue.EndToStart -> {
                                                medicationListViewModel.deleteUserMedication(
                                                    medicine
                                                )
                                                Toast.makeText(
                                                    context,
                                                    "Item archived",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }

                                            SwipeToDismissBoxValue.StartToEnd -> {}

                                            SwipeToDismissBoxValue.Settled -> return@rememberSwipeToDismissBoxState false
                                        }
                                        return@rememberSwipeToDismissBoxState true
                                    },
                                    positionalThreshold = { it * .25f }
                                )

                                SwipeToDismissBox(
                                    state = dismissState,
                                    modifier = Modifier.fillMaxWidth(),
                                    backgroundContent = {
                                        Spacer(modifier = Modifier.weight(1f))
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .background(Color(0xFFF5F5F9))
                                                .clip(RoundedCornerShape(8.dp))
                                        ) {
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxSize()
                                                    .background(Color.Red)
                                                    .clip(RoundedCornerShape(8.dp))
                                            ) {
                                                Text(
                                                    "Delete",
                                                    style = TextStyle(
                                                        fontSize = 16.sp,
                                                        color = Color.White
                                                    ),
                                                    modifier = Modifier
                                                        .align(Alignment.CenterEnd)
                                                        .padding(16.dp)
                                                )
                                            }
                                        }
                                    },
                                    enableDismissFromStartToEnd = false,
                                    content = {
                                        MedicationItem(conceptProperty = medicine) {}
                                    })

                                Spacer(modifier = Modifier.height(2.dp))
                            }
                        }
                    }
                }

                else -> {}
            }
        }
    }
}