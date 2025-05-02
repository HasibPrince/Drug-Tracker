package com.hasib.startup.ui.details

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hasib.startup.data.model.ConceptProperty
import com.hasib.startup.data.model.RxTermsResponse
import com.hasib.startup.data.repositories.DrugRepository
import com.hasib.startup.data.repositories.UserMedicationRepository
import com.hasib.startup.domian.model.Result
import com.hasib.startup.domian.model.isSuccess
import com.hasib.startup.ui.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val drugRepository: DrugRepository,
    private val userMedicationRepository: UserMedicationRepository
) : ViewModel() {

    private val _medicationAddedState = mutableStateOf("")
    val medicationAddedState = _medicationAddedState

    fun fetchDetails(rxcui: String): StateFlow<UIState<RxTermsResponse>> {
        return flow<UIState<RxTermsResponse>> {
            emit(UIState.Loading)
            val result = drugRepository.getDrugDetailsByRxcui(rxcui)
            if (result.isSuccess()) {
                emit(UIState.Success((result as Result.Success).data))
            } else {
                emit(UIState.Error((result as Result.Error).e.message ?: "UnknownError"))
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = UIState.Idle
        )
    }

    fun addUserMedication(conceptProperty: ConceptProperty) {
        viewModelScope.launch {
            val status = userMedicationRepository.addUserMedication(conceptProperty)
            if (status.isSuccess()) {
                medicationAddedState.value = "Medication added successfully"
            } else {
                medicationAddedState.value = (status as Result.Error).e.message ?: "UnknownError"
            }
        }
    }
}