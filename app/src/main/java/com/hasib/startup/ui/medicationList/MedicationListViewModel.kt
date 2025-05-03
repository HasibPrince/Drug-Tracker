package com.hasib.startup.ui.medicationList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.hasib.startup.data.model.ConceptProperty
import com.hasib.startup.data.repositories.UserMedicationRepository
import com.hasib.startup.domian.model.Result
import com.hasib.startup.domian.model.isSuccess
import com.hasib.startup.ui.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MedicationListViewModel @Inject constructor(private val userMedicationRepository: UserMedicationRepository) :
    ViewModel() {

    private val _usersMedicationsStateFlow =
        MutableStateFlow<UIState<List<ConceptProperty>>>(UIState.Idle)
    val usersMedicationsStateFlow: StateFlow<UIState<List<ConceptProperty>>> = _usersMedicationsStateFlow

    fun getUserMedications() {
        viewModelScope.launch {
            _usersMedicationsStateFlow.emit(UIState.Loading)
            val result = userMedicationRepository.getAllUserMedications()
            handleUsersMedicationResult(result)
        }
    }

    fun deleteUserMedication(conceptProperty: ConceptProperty) {
        viewModelScope.launch {
            val result = userMedicationRepository.deleteUserMedication(conceptProperty)
            delay(500)
            handleUsersMedicationResult(result)
        }
    }

    fun logout() {
        FirebaseAuth.getInstance().signOut()
    }

    private fun handleUsersMedicationResult(
        result: Result<List<ConceptProperty>>
    ){
        if (result.isSuccess()) {
            _usersMedicationsStateFlow.value = UIState.Success((result as Result.Success).data)
        } else {
            _usersMedicationsStateFlow.value = UIState.Error((result as Result.Error).e.message ?: "UnknownError")
        }
    }
}