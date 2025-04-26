package com.firman.submission.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.firman.submission.data.ListCharacterRepository
import com.firman.submission.model.GenshinCharacter
import com.firman.submission.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: ListCharacterRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState<List<GenshinCharacter>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<GenshinCharacter>>>
        get() = _uiState

    fun getAllCharacters() {
        viewModelScope.launch {
            repository.getAllCharacter()
                .catch { exception ->
                    _uiState.value = UiState.Error(exception.message ?: "Terjadi kesalahan")
                }
                .collect { characters ->
                    _uiState.value = UiState.Success(characters)
                }
        }
    }
}
