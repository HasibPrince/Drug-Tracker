package com.hasib.startup.ui.medicationPage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hasib.startup.data.model.ConceptProperty
import com.hasib.startup.data.repositories.DrugRepository
import com.hasib.startup.data.model.Result
import com.hasib.startup.data.model.isSuccess
import com.hasib.startup.ui.ResourceProvider
import com.hasib.startup.ui.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import com.hasib.startup.R

@HiltViewModel
class SearchMedicationViewModel @Inject constructor(private val drugRepository: DrugRepository) :
    ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    var lastSearchQuery: String = ""
    var lastSearchResults: UIState<List<ConceptProperty>> = UIState.Idle

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val filteredMedications: StateFlow<UIState<List<ConceptProperty>>> = _searchQuery
        .debounce(500)
        .flatMapLatest { query ->
            flow {
                if (query.isEmpty()) {
                    emit(UIState.Success(emptyList()))
                    return@flow
                }

                if (query == lastSearchQuery && lastSearchResults is UIState.Success) {
                    emit(lastSearchResults)
                    return@flow
                }

                lastSearchQuery = query
                lastSearchResults = UIState.Idle
                emit(UIState.Loading)

                val result = drugRepository.searchDrugByName(query)
                if (result.isSuccess()) {
                    lastSearchResults = UIState.Success((result as Result.Success).data)
                    emit(lastSearchResults)
                } else {
                    emit(UIState.Error((result as Result.Error).e.message ?: ResourceProvider.getString(R.string.unknownError)))
                }
            }

        }
        .distinctUntilChanged()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), UIState.Idle)

    fun onSearchQueryChanged(newQuery: String) {
        if (newQuery == _searchQuery.value) return
        _searchQuery.value = newQuery
    }
}