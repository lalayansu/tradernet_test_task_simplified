package com.example.tradernet_test_task_simplified.presenter.home

import androidx.compose.runtime.Immutable
import com.example.tradernet_test_task_simplified.data.model.response.Quote

@Immutable
data class HomeContract(
    val data: List<Quote>,
    val isLoading: Boolean,
    val error: String?
) {
    companion object {
        fun initial(): HomeContract = HomeContract(
            data = emptyList(),
            isLoading = false,
            error = null
        )
    }
}