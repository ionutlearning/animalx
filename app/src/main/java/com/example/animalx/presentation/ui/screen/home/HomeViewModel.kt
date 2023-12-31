package com.example.animalx.presentation.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animalx.domain.ViewState
import com.example.animalx.domain.entity.Animal
import com.example.animalx.domain.repository.AnimalListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val animalListRepository: AnimalListRepository
) : ViewModel() {

    private val _animalsStateFlow: MutableStateFlow<ViewState<List<Animal>>> =
        MutableStateFlow(ViewState.Idle)
    val animalsStateFlow: StateFlow<ViewState<List<Animal>>> = _animalsStateFlow

    fun getAnimals() {
        _animalsStateFlow.update { ViewState.Loading }

        viewModelScope.launch {
            runCatching {
                animalListRepository.getAnimals()
            }.onSuccess { result ->
                _animalsStateFlow.update { ViewState.Success(result) }
            }.onFailure {error ->
                _animalsStateFlow.update { ViewState.Error(error.localizedMessage ?: "") }
            }
        }
    }
}