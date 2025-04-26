package com.firman.submission.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.firman.submission.data.ListCharacterRepository
import com.firman.submission.model.GenshinCharacter
import com.firman.submission.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: ListCharacterRepository) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState<GenshinCharacter>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<GenshinCharacter>>
        get() = _uiState

    fun getCharacterById(characterId: Long) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val character = repository.getCharacterById(characterId)
                if (character != null) {
                    _uiState.value = UiState.Success(character)
                } else {
                    _uiState.value = UiState.Error("Character not found")
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Unknown Error")
            }
        }
    }
}
