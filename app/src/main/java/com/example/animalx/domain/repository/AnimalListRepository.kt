package com.example.animalx.domain.repository

import com.example.animalx.domain.entity.Animal

interface AnimalListRepository {

    suspend fun getAnimals(): List<Animal>

}