package com.hasib.startup.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hasib.startup.data.model.ConceptProperty

@Dao
interface ConceptPropertyDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(conceptProperty: ConceptProperty): Long

    @Query("SELECT * FROM conceptProperty")
    suspend fun getConceptProperties(): List<ConceptProperty>

    @Query("DELETE FROM conceptProperty WHERE rxcui = :rxcui")
    suspend fun delete(rxcui: String)
}