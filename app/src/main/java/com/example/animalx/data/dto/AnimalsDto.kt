package com.example.animalx.data.dto

import com.example.animalx.domain.entity.Animal
import com.example.animalx.domain.entity.AnimalDetail

data class AnimalsDto(
    val animals: List<AnimalDto.Animal>,
)

data class AnimalDto(val animal: Animal) {
    data class Animal(
        val breeds: Breeds,
        val distance: Double?,
        val gender: String,
        val id: Long,
        val name: String,
        val size: String,
        val status: String,
    ) {
        data class Breeds(
            val primary: String,
        )
    }
}

fun AnimalDto.Animal.toAnimal() = Animal(
    id = id,
    name = name
)

fun AnimalDto.toAnimalDetail() = AnimalDetail(
    name = animal.name,
    breed = animal.breeds.primary,
    size = animal.size,
    gender = animal.gender,
    status = animal.status,
    distance = animal.distance
)