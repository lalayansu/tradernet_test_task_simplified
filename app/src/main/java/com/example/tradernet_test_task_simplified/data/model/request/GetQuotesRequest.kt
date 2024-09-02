package com.example.tradernet_test_task_simplified.data.model.request

data class GetQuotesRequest(
    val q: Q? = null
) {
    data class Q(
        val cmd: String? = null,
        val params: Params? = null
    ) {
        data class Params(
            val exchange: String? = null,
            val gainers: Int? = null,
            val limit: Int? = null,
            val type: String? = null
        )
    }
}