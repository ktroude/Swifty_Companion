package com.example.swifty.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.swifty.data.model.User
import com.example.swifty.repository.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: StateFlow<UiState> get() = _uiState

    fun fetchUser(login: String) {
        _uiState.value = UiState.Loading

        viewModelScope.launch {
            try {
                // Appelle le repository pour récupérer les informations utilisateur
                val user = repository.getUser(login)

                // Mise à jour de l'état : succès avec les données utilisateur
                _uiState.value = UiState.Success(user)
            } catch (e: Exception) {
                // Gestion des erreurs
                _uiState.value = UiState.Error(e.message ?: "An unexpected error occurred")
            }
        }
    }

    sealed class UiState {
        object Idle : UiState()
        object Loading : UiState()
        data class Success(val user: User) : UiState()
        data class Error(val message: String) : UiState()
    }
}

