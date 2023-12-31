package com.example.animalx.presentation.ui.screen.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.animalx.domain.ViewState
import com.example.animalx.domain.entity.AnimalDetail
import com.example.animalx.domain.repository.AnimalDetailRepository
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
class DetailViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val repository: AnimalDetailRepository = mockk()

    private lateinit var detailViewModel: DetailViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        detailViewModel = DetailViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getAnimalDetail emits correct values`() = runTest {
        // Given
        coEvery { repository.getAnimalDetail(1) } returns mockedAnimal

        val results = mutableListOf<ViewState<AnimalDetail>>()
        val job = launch {
            detailViewModel.animalStateFlow.toList(results)
        }

        // When
        detailViewModel.getAnimalDetails(1)
        advanceUntilIdle()

        // Then
        assertEquals(ViewState.Loading, results[0])
        assertEquals(ViewState.Success(mockedAnimal), results[1])

        job.cancel()
    }

    @Test
    fun `getAnimalDetail emits error`() = runTest {
        // Given
        coEvery { repository.getAnimalDetail(1) }  throws RuntimeException("error")

        val results = mutableListOf<ViewState<AnimalDetail>>()
        val job = launch {
            detailViewModel.animalStateFlow.toList(results)
        }

        // When
        detailViewModel.getAnimalDetails(1)
        advanceUntilIdle()

        // Then
        assertEquals(ViewState.Loading, results[0])
        assertEquals(ViewState.Error("error"), results[1])

        job.cancel()
    }

    private val mockedAnimal = AnimalDetail(
        "Rex",
        "lup",
        "large",
        "M",
        "Single",
        123.0
    )
}