package com.hasib.startup.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

data class DrugResponse(
    val drugGroup: DrugGroup?
)

data class DrugGroup(
    val name: String?,
    val conceptGroup: List<ConceptGroup>
)

data class ConceptGroup(
    val tty: String,
    val conceptProperties: List<ConceptProperty>
)


@Parcelize
@Entity
data class ConceptProperty(
    @PrimaryKey
    val rxcui: String,
    val name: String,
    val synonym: String,
    val tty: String,
    val language: String,
    val suppress: String,
    val umlscui: String
): Parcelable