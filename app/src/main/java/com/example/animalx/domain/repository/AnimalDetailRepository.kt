package com.example.animalx.domain.repository

import com.example.animalx.domain.entity.AnimalDetail

interface AnimalDetailRepository {

    suspend fun getAnimalDetail(id: Long): AnimalDetail

}