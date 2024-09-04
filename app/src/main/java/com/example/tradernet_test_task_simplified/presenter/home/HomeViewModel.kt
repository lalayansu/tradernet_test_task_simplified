package com.example.tradernet_test_task_simplified.presenter.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tradernet_test_task_simplified.data.model.request.GetQuotesRequest
import com.example.tradernet_test_task_simplified.data.repo.ConnectionState
import com.example.tradernet_test_task_simplified.data.repo.QuoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuoteViewModel @Inject constructor(
    private val quoteRepository: QuoteRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeContract.initial())
    val uiState = _uiState.asStateFlow()

    init {
        fetchAndSubscribeQuotes(
            request = GetQuotesRequest(
                q = GetQuotesRequest.Q(
                    cmd = "getTopSecurities",
                    params = GetQuotesRequest.Q.Params(
                        exchange = "russia",
                        gainers = 0,
                        limit = 30,
                        type = "stocks"
                    )
                )
            )
        )
    }

    private fun fetchAndSubscribeQuotes(request: GetQuotesRequest) {
        _uiState.update { currentState ->
            currentState.copy(
                isLoading = true
            )
        }

        viewModelScope.launch(Dispatchers.IO) {
            quoteRepository.fetchAndSubscribeQuotes(request).collect { connectionState ->
                when (connectionState) {
                    is ConnectionState.Connected -> _uiState.update { currentState ->
                        currentState.copy(
                            isLoading = false
                        )
                    }

                    ConnectionState.Disconnected -> _uiState.update { currentState ->
                        currentState.copy(
                            isLoading = false
                        )
                    }

                    is ConnectionState.Success -> _uiState.update { currentState ->
                        currentState.copy(
                            data = connectionState.data,
                            isLoading = false
                        )
                    }

                    is ConnectionState.Error -> _uiState.update { currentState ->
                        currentState.copy(
                            error = connectionState.errorMessage,
                            isLoading = false
                        )
                    }
                }
            }
        }
    }

    fun onQuoteAnimationEnd(ticker: String) = viewModelScope.launch(Dispatchers.IO) {
        quoteRepository.onAnimationEnd(ticker)
    }
}