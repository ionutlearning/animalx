package com.example.animalx.di

import com.example.animalx.data.repository.AnimalDetailRepositoryImpl
import com.example.animalx.data.repository.AnimalListRepositoryImpl
import com.example.animalx.domain.repository.AnimalDetailRepository
import com.example.animalx.domain.repository.AnimalListRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindAnimalListRepository(impl: AnimalListRepositoryImpl): AnimalListRepository

    @Binds
    abstract fun bindAnimalDetailRepository(impl: AnimalDetailRepositoryImpl): AnimalDetailRepository
}