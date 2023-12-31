package com.example.animalx.data.service

import com.example.animalx.data.dto.AnimalDto
import com.example.animalx.data.dto.AnimalsDto
import retrofit2.http.GET
import retrofit2.http.Path

interface AnimalsService {

    @GET("animals")
    suspend fun getAnimals(): AnimalsDto

    @GET("animals/{id}")
    suspend fun getAnimal(@Path("id") id: Long): AnimalDto

}