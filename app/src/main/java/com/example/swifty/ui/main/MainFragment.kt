package com.example.swifty.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.swifty.R
import com.example.swifty.repository.Repository
import com.example.swifty.viewmodel.MainViewModel
import com.example.swifty.viewmodel.MainViewModelFactory

/**
 * The main fragment that allows the user to search for a user by login.
 */
class MainFragment : Fragment() {

    /**
     * Repository instance initialized with API credentials.
     */
    private val repository = Repository(
        clientId = "",
        clientSecret = ""
    )

    /**
     * ViewModel instance used to manage UI-related data and handle API calls.
     */
    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(repository);
    }

    /**
     * Inflates the fragment's layout.
     *
     * @param inflater The layout inflater.
     * @param container The container holding this fragment.
     * @param savedInstanceState The saved state of the fragment.
     * @return The root view of the fragment.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    /**
     * Sets up the UI components and handles user interactions.
     *
     * @param view The root view of the fragment.
     * @param savedInstanceState The saved state of the fragment.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize views
        val searchButton = view.findViewById<Button>(R.id.buttonSearch);
        val loginEditText = view.findViewById<EditText>(R.id.editTextLogin);

        // Observe UI state changes
        observeUiState();

        // Handle search button click
        searchButton.setOnClickListener {
            val login = loginEditText.text.toString().trim();

            if (login.isEmpty()) {
                // Display a toast if the input is empty
                Toast.makeText(requireContext(), "Please enter a login", Toast.LENGTH_SHORT).show();
            } else {
                // Fetch user data
                fetchUser(login);
            }
        }
    }

    /**
     * Initiates the process to fetch user data based on the provided login.
     *
     * @param login The login of the user to fetch.
     */
    private fun fetchUser(login: String) {
        viewModel.fetchUser(login);
    }

    /**
     * Observes changes in the UI state and updates the UI accordingly.
     */
    private fun observeUiState() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.uiState.collect { event ->
                // Handle state if it has not been handled already
                event.getContentIfNotHandled()?.let { state ->
                    when (state) {
                        is MainViewModel.UiState.Idle -> {
                            // No operation needed for the idle state
                        }
                        is MainViewModel.UiState.Loading -> {
                            // Show a loading message
                            Toast.makeText(requireContext(), "Loading...", Toast.LENGTH_SHORT).show();
                        }
                        is MainViewModel.UiState.Success -> {
                            // Navigate to the DetailFragment with the retrieved user data
                            val action = MainFragmentDirections.actionMainFragmentToDetailFragment(state.user);
                            findNavController().navigate(action)
                        }
                        is MainViewModel.UiState.Error -> {
                            // Display an error message
                            Toast.makeText(requireContext(), "Error: ${state.message}", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        }
    }
}
