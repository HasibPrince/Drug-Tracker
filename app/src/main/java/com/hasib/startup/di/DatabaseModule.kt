package com.hasib.startup.di

import android.content.Context
import com.hasib.startup.data.database.AppDatabase
import com.hasib.startup.data.database.ConceptPropertyDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideConceptPropertyDao(@ApplicationContext context: Context): ConceptPropertyDao {
        return AppDatabase.getDatabase(context).conceptPropertyDao()
    }
}