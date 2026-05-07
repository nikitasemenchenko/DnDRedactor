package com.example.dndredactor.di

import android.content.Context
import androidx.room.Room
import com.example.dndredactor.data.AppConstants
import com.example.dndredactor.data.local.CharacterDao
import com.example.dndredactor.data.local.DndDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): DndDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = DndDatabase::class.java,
            name = AppConstants.DB_NAME
        ).build()
    }

    @Provides
    fun provideCharacterDao(
        database: DndDatabase
    ): CharacterDao {
        return database.characterDao()
    }
}