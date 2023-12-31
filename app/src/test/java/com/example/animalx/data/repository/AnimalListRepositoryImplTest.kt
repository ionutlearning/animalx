package com.example.animalx.data.repository

import com.example.animalx.data.dto.AnimalDto
import com.example.animalx.data.dto.AnimalsDto
import com.example.animalx.data.dto.toAnimal
import com.example.animalx.data.service.AnimalsService
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class AnimalListRepositoryImplTest {

    private val animalsService: AnimalsService = mockk()

    private lateinit var repository: AnimalListRepositoryImpl

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        repository = AnimalListRepositoryImpl(animalsService, StandardTestDispatcher())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getAnimals returns correct animals`() = runTest {
        // Given
        val expectedAnimals = animalsDto.animals.map { it.toAnimal()}
        coEvery { animalsService.getAnimals() } returns animalsDto

        // When
        val result = repository.getAnimals()

        // Then
        assertEquals(expectedAnimals, result)
    }

    private val animalsDto = AnimalsDto(listOf(
        AnimalDto.Animal(AnimalDto.Animal.Breeds("husky"),12.2, "M",1,"Rex", "Large", "bad"),
       AnimalDto.Animal(AnimalDto.Animal.Breeds("Lup"),12.3, "M",12,"Max", "Large", "good")
    ))
}