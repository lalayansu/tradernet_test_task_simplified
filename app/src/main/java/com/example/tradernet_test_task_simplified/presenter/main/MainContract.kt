package com.example.tradernet_test_task_simplified.presenter.main

import androidx.compose.runtime.Immutable

@Immutable
data class MainContract(
    val startRoute: String?
) {
    companion object {
        fun initial(): MainContract = MainContract(
            startRoute = null
        )
    }
}