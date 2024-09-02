package com.example.tradernet_test_task_simplified.data.service

import com.tinder.scarlet.WebSocket
import com.tinder.scarlet.ws.Receive
import com.tinder.scarlet.ws.Send
import kotlinx.coroutines.flow.Flow

interface QuoteScarletService {
    @Receive
    fun observeEvent(): Flow<WebSocket.Event>

    @Send
    fun sendSubscribe(subscribe: String)
}