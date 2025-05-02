package com.hasib.startup.ui.medicationSearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hasib.startup.data.model.ConceptProperty
import com.hasib.startup.data.repositories.DrugRepository
import com.hasib.startup.domian.model.Result
import com.hasib.startup.domian.model.isSuccess
import com.hasib.startup.ui.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SearchMedicationViewModel @Inject constructor(private val drugRepository: DrugRepository) :
    ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val filteredMedications: StateFlow<UIState<List<ConceptProperty>>> = _searchQuery
        .debounce(500)
        .flatMapLatest { query ->
            flow {
                if (query.isEmpty()) {
                    emit(UIState.Success(emptyList()))
                    return@flow
                }

                emit(UIState.Loading)

                val result = drugRepository.searchDrugByName(query)
                if (result.isSuccess()) {
                    emit(UIState.Success((result as Result.Success).data))
                } else {
                    emit(UIState.Success(emptyList()))
                }
            }

        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), UIState.Idle)

    fun onSearchQueryChanged(newQuery: String) {
        _searchQuery.value = newQuery
    }
}