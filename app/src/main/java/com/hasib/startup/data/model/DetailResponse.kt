package com.hasib.startup.data.model

data class RxTermsResponse(
    val rxtermsProperties: RxTermsProperties?
)

data class RxTermsProperties(
    val brandName: String,
    val displayName: String,
    val synonym: String,
    val fullName: String,
    val fullGenericName: String,
    val strength: String,
    val rxtermsDoseForm: String,
    val route: String,
    val termType: String,
    val rxcui: String,
    val genericRxcui: String,
    val rxnormDoseForm: String,
    val suppress: String
)