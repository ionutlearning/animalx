package com.example.animalx.presentation.ui.screen.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.animalx.domain.ViewState
import com.example.animalx.domain.entity.Animal
import com.example.animalx.domain.repository.AnimalListRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val repository: AnimalListRepository = mockk()

    private lateinit var homeViewModel: HomeViewModel

    @Before
    fun setUp() {
        homeViewModel = HomeViewModel(repository)
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getAnimals emits correct values`() = runTest {
        // Given
        coEvery { repository.getAnimals() } returns mockedAnimals

        val results = mutableListOf<ViewState<List<Animal>>>()
        val job = launch {
            homeViewModel.animalsStateFlow.toList(results)
        }

        // When
        homeViewModel.getAnimals()
        advanceUntilIdle()

        // Then
        assertEquals(ViewState.Loading, results[0])
        assertEquals(ViewState.Success(mockedAnimals), results[1])

        job.cancel()
    }

    @Test
    fun `getAnimals emits error`() = runTest {
        // Given
        coEvery { repository.getAnimals() } throws RuntimeException("error")

        val results = mutableListOf<ViewState<List<Animal>>>()
        val job = launch {
            homeViewModel.animalsStateFlow.toList(results)
        }

        // When
        homeViewModel.getAnimals()
        advanceUntilIdle()

        // Then
        assertEquals(ViewState.Loading, results[0])
        assertEquals(ViewState.Error("error"), results[1])

        job.cancel()
    }

    private val mockedAnimals = listOf(
        Animal(
            1,
            "Rex"
        ),
        Animal(
            2,
            "Max"
        )
    )
}
