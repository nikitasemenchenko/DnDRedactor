package com.example.dndredactor.di

import com.example.dndredactor.data.repository.LocalCharacterRepositoryImpl
import com.example.dndredactor.domain.repository.LocalCharacterRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindLocalCharacterRepository(
        impl: LocalCharacterRepositoryImpl
    ): LocalCharacterRepository

}