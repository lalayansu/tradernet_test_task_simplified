package com.example.tradernet_test_task_simplified.data.repo

import com.example.tradernet_test_task_simplified.data.model.response.Quote

sealed interface ConnectionState {
    data object Connected : ConnectionState
    data object Disconnected : ConnectionState
    data class Error(val errorMessage: String) : ConnectionState
    data class Success(val data: List<Quote>) : ConnectionState
}
