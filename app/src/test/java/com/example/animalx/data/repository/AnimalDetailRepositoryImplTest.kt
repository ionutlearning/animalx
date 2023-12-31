package com.example.animalx.data.repository

import com.example.animalx.data.dto.AnimalDto
import com.example.animalx.data.dto.toAnimalDetail
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
class AnimalDetailRepositoryImplTest {

    private val animalsService: AnimalsService = mockk()

    private lateinit var repository: AnimalDetailRepositoryImpl

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        repository = AnimalDetailRepositoryImpl(animalsService, StandardTestDispatcher())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getAnimal returns correct animal`() = runTest {
        // Given
        val expectedAnimalDetail = animalsDto.toAnimalDetail()
        coEvery { animalsService.getAnimal(1) } returns animalsDto

        // When
        val result = repository.getAnimalDetail(1)

        // Then
        assertEquals(expectedAnimalDetail, result)
    }

    private val animalsDto =
        AnimalDto(AnimalDto.Animal(AnimalDto.Animal.Breeds("husky"),12.2, "M",1,"Rex", "Large", "bad"))
}