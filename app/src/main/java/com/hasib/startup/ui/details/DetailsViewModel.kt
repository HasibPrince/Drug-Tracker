package com.hasib.startup.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hasib.startup.data.model.RxTermsResponse
import com.hasib.startup.data.repositories.DrugRepository
import com.hasib.startup.domian.model.Result
import com.hasib.startup.domian.model.isSuccess
import com.hasib.startup.ui.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val drugRepository: DrugRepository) : ViewModel() {

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
}