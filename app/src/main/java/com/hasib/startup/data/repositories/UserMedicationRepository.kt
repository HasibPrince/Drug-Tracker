package com.hasib.startup.data.repositories

import com.hasib.startup.data.database.ConceptPropertyDao
import com.hasib.startup.data.model.ConceptProperty
import com.hasib.startup.domian.model.Result
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserMedicationRepository @Inject constructor(private val conceptPropertyDao: ConceptPropertyDao) {
    suspend fun addUserMedication(conceptProperty: ConceptProperty): Result<Unit> {
        val usersMedicationList = conceptPropertyDao.getConceptProperties()
        if (usersMedicationList.size >= 3) {
            return Result.Error(Exception("User medication limit reached"))
        }

        val insertStatus = conceptPropertyDao.insert(conceptProperty)
        if (insertStatus == -1L) {
            return Result.Error(Exception("Already Added!"))
        }
        return Result.Success(Unit)
    }

    suspend fun getAllUserMedications(): Result<List<ConceptProperty>> {
        val conceptProperties = conceptPropertyDao.getConceptProperties()
        if (conceptProperties.isEmpty()) {
            return Result.Error(Exception("No added medications found"))
        }

        return Result.Success(conceptProperties)
    }

    suspend fun deleteUserMedication(conceptProperty: ConceptProperty): Result<List<ConceptProperty>> {
        conceptPropertyDao.delete(conceptProperty.rxcui)
        return getAllUserMedications()
    }
}