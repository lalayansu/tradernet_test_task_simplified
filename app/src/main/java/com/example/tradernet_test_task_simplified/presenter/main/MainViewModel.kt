package com.example.tradernet_test_task_simplified.presenter.main


import androidx.lifecycle.ViewModel
import com.example.tradernet_test_task_simplified.presenter.navigation.Destinations
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(MainContract.initial())
    val uiState = _uiState.asStateFlow()

    init {
        defineAppStartRoute()
    }

    private fun defineAppStartRoute() = _uiState.update { state ->
        state.copy(startRoute = Destinations.HOME_SCREEN_DESTINATION)
    }
}