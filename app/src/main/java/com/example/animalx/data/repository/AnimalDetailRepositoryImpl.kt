package com.example.animalx.data.repository

import com.example.animalx.data.dto.toAnimalDetail
import com.example.animalx.data.service.AnimalsService
import com.example.animalx.di.DispatcherModule
import com.example.animalx.domain.entity.AnimalDetail
import com.example.animalx.domain.repository.AnimalDetailRepository
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class AnimalDetailRepositoryImpl @Inject constructor(
    private val animalsService: AnimalsService,
    @DispatcherModule.IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : AnimalDetailRepository {

    override suspend fun getAnimalDetail(id: Long): AnimalDetail =
        withContext(ioDispatcher) {
            animalsService.getAnimal(id).toAnimalDetail()
        }

}