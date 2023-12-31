package com.example.animalx.presentation.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animalx.domain.ViewState
import com.example.animalx.domain.entity.AnimalDetail
import com.example.animalx.domain.repository.AnimalDetailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


@HiltViewModel
class DetailViewModel @Inject constructor(
    private val animalDetailRepository: AnimalDetailRepository
) : ViewModel() {

    private val _animalStateFlow: MutableStateFlow<ViewState<AnimalDetail>> =
        MutableStateFlow(ViewState.Idle)
    val animalStateFlow: StateFlow<ViewState<AnimalDetail>> = _animalStateFlow

    fun getAnimalDetails(id: Long) {
        _animalStateFlow.update { ViewState.Loading }

        viewModelScope.launch {
            runCatching {
                animalDetailRepository.getAnimalDetail(id)
            }.onSuccess { result ->
                _animalStateFlow.update { ViewState.Success(result) }
            }.onFailure {error ->
                _animalStateFlow.update { ViewState.Error(error.localizedMessage ?: "") }
            }
        }
    }
}