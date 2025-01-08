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

class MainFragment : Fragment() {

    // Repository avec les identifiants API
    private val repository = Repository(
        clientId = "",
        clientSecret = ""
    )

    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val searchButton = view.findViewById<Button>(R.id.buttonSearch)
        val loginEditText = view.findViewById<EditText>(R.id.editTextLogin)

        observeUiState()

        searchButton.setOnClickListener {
            val login = loginEditText.text.toString().trim()

            if (login.isEmpty()) {
                Toast.makeText(requireContext(), "Please enter a login", Toast.LENGTH_SHORT).show()
            } else {
                fetchUser(login)
            }
        }
    }

    private fun fetchUser(login: String) {
        viewModel.fetchUser(login)
    }

    private fun observeUiState() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.uiState.collect { state ->
                when (state) {
                    is MainViewModel.UiState.Idle -> {}
                    is MainViewModel.UiState.Loading -> {
                        Toast.makeText(requireContext(), "Loading...", Toast.LENGTH_SHORT).show()
                    }
                    is MainViewModel.UiState.Success -> {
                        val action = MainFragmentDirections.actionMainFragmentToDetailFragment(state.user)
                        findNavController().navigate(action)
                    }
                    is MainViewModel.UiState.Error -> {
                        Toast.makeText(requireContext(), "Error: ${state.message}", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}
