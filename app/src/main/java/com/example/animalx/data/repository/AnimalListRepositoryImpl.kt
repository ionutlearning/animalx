package com.example.animalx.data.repository

import com.example.animalx.data.dto.toAnimal
import com.example.animalx.data.service.AnimalsService
import com.example.animalx.di.DispatcherModule
import com.example.animalx.domain.entity.Animal
import com.example.animalx.domain.repository.AnimalListRepository
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class AnimalListRepositoryImpl @Inject constructor(
    private val animalsService: AnimalsService,
    @DispatcherModule.IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : AnimalListRepository {

    override suspend fun getAnimals(): List<Animal> =
        withContext(ioDispatcher) {
            animalsService.getAnimals().animals.map { animalDto ->
                animalDto.toAnimal()
            }
        }

}