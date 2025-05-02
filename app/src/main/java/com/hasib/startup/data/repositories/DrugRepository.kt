package com.hasib.startup.data.repositories

import com.hasib.startup.data.api.DrugApiService
import com.hasib.startup.data.api.handleDataFetch
import com.hasib.startup.data.model.ConceptProperty
import com.hasib.startup.domian.model.Result
import com.hasib.startup.domian.model.doOnSuccess
import com.hasib.startup.domian.model.isSuccess
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DrugRepository @Inject constructor(private val drugApiService: DrugApiService) {
    suspend fun searchDrugByName(name: String): Result<List<ConceptProperty>> {
        val result = handleDataFetch {
            drugApiService.getDrugsByName(name)
        }

        if (result.isSuccess()) {
            val drugResponse = (result as Result.Success).data
            val conceptGroup =
                drugResponse.drugGroup?.conceptGroup?.find { it.tty == "SBD" }?.conceptProperties?.let {
                    it.subList(0, minOf(9, it.size))
                }
            return Result.Success(conceptGroup ?: emptyList())
        }

        return result as Result.Error
    }
}