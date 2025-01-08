package com.example.swifty.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.swifty.data.model.User
import com.example.swifty.repository.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel class responsible for managing the UI state for the main screen.
 * Handles user data retrieval and maintains state for the MainFragment.
 */
class MainViewModel(private val repository: Repository) : ViewModel() {

    /**
     * Backing property for UI state.
     * Encapsulated to expose only immutable state to observers.
     */
    private val _uiState = MutableStateFlow<Event<UiState>>(Event(UiState.Idle));

    /**
     * Publicly exposed state flow that allows observing changes in the UI state.
     */
    val uiState: StateFlow<Event<UiState>> get() = _uiState;

    /**
     * Initiates the process to fetch user data by login.
     *
     * @param login The login identifier for the user to fetch.
     */
    fun fetchUser(login: String) {
        _uiState.value = Event(UiState.Loading);

        viewModelScope.launch {
            try {
                // Fetch user data from the repository
                val user = repository.getUser(login);
                // Update the state to Success with retrieved user data
                _uiState.value = Event(UiState.Success(user));
            } catch (e: Exception) {
                // Update the state to Error with an error message
                _uiState.value = Event(UiState.Error(e.message ?: "An unexpected error occurred"));
            }
        }
    }

    /**
     * Represents the different states of the UI.
     */
    sealed class UiState {
        /**
         * Idle state when no operation is in progress.
         */
        object Idle : UiState();

        /**
         * Loading state when an operation is in progress.
         */
        object Loading : UiState();

        /**
         * Success state with the retrieved user data.
         *
         * @param user The successfully fetched user data.
         */
        data class Success(val user: User) : UiState();

        /**
         * Error state with an associated error message.
         *
         * @param message The error message to display.
         */
        data class Error(val message: String) : UiState();
    }
}

/**
 * Wrapper class for events to ensure they are handled only once.
 *
 * @param T The type of content encapsulated in the event.
 */
class Event<out T>(private val content: T) {
    private var hasBeenHandled = false;

    /**
     * Retrieves the content only if it hasn't been handled.
     *
     * @return The content if it hasn't been handled, null otherwise.
     */
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null;
        } else {
            hasBeenHandled = true;
            content;
        }
    }

    /**
     * Retrieves the content without considering whether it has been handled.
     *
     * @return The content of the event.
     */
    fun peekContent(): T = content;
}
