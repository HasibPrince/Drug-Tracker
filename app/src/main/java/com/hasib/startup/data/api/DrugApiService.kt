package com.hasib.startup.data.api

import com.hasib.startup.data.model.DrugResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL = "https://rxnav.nlm.nih.gov/REST/"

interface DrugApiService {
    @GET("drugs.json")
    suspend fun getDrugsByName(
        @Query("name") name: String
    ): Response<DrugResponse>
}