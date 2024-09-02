package com.example.tradernet_test_task_simplified.data.service

import com.example.tradernet_test_task_simplified.data.model.request.GetQuotesRequest
import com.example.tradernet_test_task_simplified.data.model.response.QuotesListModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface QuoteService {
    @POST("api/")
    fun getQuotesList(
        @Body request: GetQuotesRequest
    ): Call<QuotesListModel>
}