package com.hasib.startup.ui.medicationSearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class SearchMedicationViewModel : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _allMedications = listOf(
        "Medicine 1", "Medicine 2", "Paracetamol", "Ibuprofen", "Aspirin"
    )

    val filteredMedications: StateFlow<List<String>> = _searchQuery
        .map { query ->
            if (query.isBlank()) _allMedications
            else _allMedications.filter {
                it.contains(query.trim(), ignoreCase = true)
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), _allMedications)

    fun onSearchQueryChanged(newQuery: String) {
        _searchQuery.value = newQuery
    }
}