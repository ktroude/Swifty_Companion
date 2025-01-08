package com.example.swifty.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.swifty.repository.Repository

/**
 * Factory class responsible for creating instances of the `MainViewModel` class.
 * Ensures that the `MainViewModel` is initialized with the required dependencies.
 *
 * @param repository The repository instance required by `MainViewModel` for data operations.
 */
class MainViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {

    /**
     * Creates an instance of the specified `ViewModel` class.
     *
     * @param modelClass The class of the ViewModel to be created.
     * @return An instance of the requested ViewModel.
     * @throws IllegalArgumentException If the requested ViewModel class is not `MainViewModel`.
     */
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            // Return an instance of MainViewModel with the provided repository
            return MainViewModel(repository) as T;
        }
        // Throw an exception if the ViewModel class is unknown
        throw IllegalArgumentException("Unknown ViewModel class");
    }
}
