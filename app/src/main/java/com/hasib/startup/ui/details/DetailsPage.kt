package com.hasib.startup.ui.details

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hasib.startup.R
import com.hasib.startup.data.model.ConceptProperty
import com.hasib.startup.ui.AppBar
import com.hasib.startup.ui.UIState

@Composable
fun MedicationDetailsPage(
    conceptProperty: ConceptProperty,
    detailsViewModel: DetailsViewModel,
    onBack: () -> Unit = {},
) {
    val detailsData by detailsViewModel.fetchDetails(conceptProperty.rxcui).collectAsState()
    val medicationAddedState by detailsViewModel.medicationAddedState

    Scaffold(
        topBar = {
            AppBar(onBack)
        },
        containerColor = Color(0xFFF5F5F9)
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_capsule),
                        contentDescription = null,
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(conceptProperty.name, fontWeight = FontWeight.Bold, fontSize = 20.sp, style = TextStyle(textAlign = TextAlign.Center))
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(conceptProperty.synonym, color = Color.Gray, fontSize = 14.sp, style = TextStyle(textAlign = TextAlign.Center))
                }

                Spacer(modifier = Modifier.height(16.dp))

                Card(
                    modifier = Modifier
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                            .background(color = Color.White)
                    ) {
                        Text("Details", fontWeight = FontWeight.Medium, color = Color.Gray)
                        Spacer(modifier = Modifier.height(8.dp))

                        when (detailsData) {
                            is UIState.Loading -> {
                                CircularProgressIndicator(
                                    modifier = Modifier.align(Alignment.CenterHorizontally),
                                    color = Color(0xFF007BFF)
                                )
                            }

                            is UIState.Error -> {
                                Text(
                                    (detailsData as UIState.Error).message,
                                    style = TextStyle(
                                        fontSize = 14.sp,
                                        textAlign = TextAlign.Center
                                    ),
                                    modifier = Modifier.align(Alignment.CenterHorizontally)
                                )
                            }

                            is UIState.Success -> {
                                val detailsData =
                                    (detailsData as UIState.Success).data.rxtermsProperties
                                detailsData?.let {
                                    Text(
                                        style = TextStyle(fontSize = 14.sp),
                                        text = buildAnnotatedString {
                                            append("Medication Name: ${conceptProperty.name}\n")
                                            append("Medication Synonym: ${conceptProperty.synonym}\n")
                                            append("Medication Rxcui: ${conceptProperty.rxcui}\n")
                                            append("Medication Type: ${conceptProperty.tty}\n\n")
                                            append("Medication Dosage Form: ${conceptProperty.umlscui}\n")
                                            append("Medication Language: ${conceptProperty.language}\n")
                                            append("Medication Dosage Form: ${conceptProperty.suppress}\n\n")
                                            append("Full Name: ${detailsData.fullName}\n")
                                            append("Display Name: ${detailsData.displayName}\n")
                                            append("Full Generic Name: ${detailsData.fullGenericName}\n")
                                            append("Synonym: ${detailsData.synonym}\n")
                                            append("Rxcui: ${detailsData.rxcui}\n")
                                            append("GenericRxCui: ${detailsData.genericRxcui}\n")
                                            append("Rout: ${detailsData.route}\n")
                                            append("XNormDoseFrom: ${detailsData.rxnormDoseForm}\n")
                                            append("RxTermDoseFrom: ${detailsData.rxtermsDoseForm}\n")
                                            append("Strength: ${detailsData.strength}\n")
                                            append("TermType: ${detailsData.termType}\n\n")
                                            append("Tablet:\n")
                                            append("• Adult: 1-2 tablets every 4 to 6 hours up to a maximum of 4 gm (8 tablets) daily.\n")
                                            append("• Children (6-12 years): ½ to 1 tablet 3 to 4 times daily. For long term treatment it is wise not to exceed the dose beyond 2.6 gm/day.\n\n")
                                            append("Extended Release Tablet:\n")
                                            append("• Adults & Children over 12 years: Two tablets, swallowed whole, every 6 to 8 hours (maximum of 6 tablets in any 24 hours). The tablet must not be crushed.\n\n")
                                            append("Syrup/Suspension:\n")
                                            append("• Children under 3 months: 10 mg/kg body weight (reduce to 5 mg/kg if needed).")
                                        },
                                        fontSize = 16.sp
                                    )
                                }
                            }

                            UIState.Idle -> {}
                        }

                        if (medicationAddedState.isNotEmpty()) {
                            Toast.makeText(
                                LocalContext.current,
                                medicationAddedState,
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    }
                }

                Spacer(modifier = Modifier.height(80.dp)) // Leave space above button
            }

            Box(
                modifier = Modifier
                    .height(90.dp)
                    .align(Alignment.BottomCenter)
            ) {
                Button(
                    onClick = { detailsViewModel.addUserMedication(conceptProperty) },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF007BFF)
                    ),
                    shape = RoundedCornerShape(12.dp),
                ) {
                    Text("Add Medication to List", color = Color.White)
                }
            }
        }
    }
}