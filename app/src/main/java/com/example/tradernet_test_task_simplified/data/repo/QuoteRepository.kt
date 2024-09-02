package com.example.tradernet_test_task_simplified.data.repo

import com.example.tradernet_test_task_simplified.data.model.request.GetQuotesRequest
import com.example.tradernet_test_task_simplified.data.model.response.Quote
import com.example.tradernet_test_task_simplified.data.model.response.QuoteModel
import com.example.tradernet_test_task_simplified.data.model.response.toQuote
import com.example.tradernet_test_task_simplified.data.service.QuoteScarletService
import com.example.tradernet_test_task_simplified.data.service.QuoteService
import com.google.gson.Gson
import com.tinder.scarlet.Message
import com.tinder.scarlet.WebSocket
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import retrofit2.awaitResponse
import java.util.concurrent.LinkedBlockingQueue
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuoteRepository @Inject constructor(
    private val quoteService: QuoteService,
    private val quoteScarletService: QuoteScarletService,
) {
    private val _tickerUpdates = MutableStateFlow<List<Quote>>(emptyList())
    private val tickerUpdates: StateFlow<List<Quote>> = _tickerUpdates.asStateFlow()

    private val updateMutex = Mutex()
    private val quoteQueue = LinkedBlockingQueue<Quote>()

    init {
        CoroutineScope(Dispatchers.IO).launch {
            quoteScarletService.observeEvent().collect { event ->
                if (event is WebSocket.Event.OnMessageReceived) {
                    val message = (event.message as Message.Text).value
                    val jsonArray = Gson().fromJson(message, Array::class.java)

                    if (jsonArray != null && jsonArray.size == 2 && jsonArray[0] == "q") {
                        val quoteModelJson = Gson().toJson(jsonArray[1])
                        val quoteModel = Gson().fromJson(quoteModelJson, QuoteModel::class.java)

                        if (quoteModel != null) {
                            addQuoteToQueue(quoteModel.toQuote())
                        }
                    }
                }
            }
        }
    }

    private fun addQuoteToQueue(quote: Quote) {
        quoteQueue.offer(quote)
        processQuoteQueue()
    }

    private fun processQuoteQueue() {
        CoroutineScope(Dispatchers.IO).launch {
            while (quoteQueue.isNotEmpty()) {
                val quote = quoteQueue.poll()
                quote?.let {
                    processQuote(it)
                }
            }
        }
    }

    private suspend fun processQuote(quote: Quote) = updateMutex.withLock {
        val currentList = _tickerUpdates.value.toMutableList()
        val index = currentList.indexOfFirst { it.ticker == quote.ticker }
        if (index != -1) {
            val updatedQuote = currentList[index].mergeWith(quote)
            currentList[index] = updatedQuote
        } else {
            currentList.add(quote)
        }
        _tickerUpdates.value = currentList
    }

    fun fetchAndSubscribeQuotes(request: GetQuotesRequest): Flow<ConnectionState> = flow {
        val quoteFields = fetchQuotes(request).first()
        if (quoteFields.isNotEmpty()) {
            subscribeQuotes(quoteFields).collect { connectionState ->
                emit(connectionState)
            }
        } else {
            emit(ConnectionState.Disconnected)
        }
    }

    private fun fetchQuotes(request: GetQuotesRequest): Flow<List<String>> = flow {
        val response = quoteService.getQuotesList(request).awaitResponse()
        if (response.isSuccessful) {
            val quotesListModel = response.body()
            val quoteFields = quotesListModel?.tickers?.map { it.orEmpty() }.orEmpty()
            emit(quoteFields)
        } else {
            emit(emptyList())
        }
    }

    private fun subscribeQuotes(quoteFields: List<String>): Flow<ConnectionState> = flow {
        if (quoteFields.isNotEmpty()) {
            observeEventAndQuote(quoteFields).collect { connectionState ->
                emit(connectionState)
            }
        } else {
            emit(ConnectionState.Disconnected)
        }
    }

    private fun observeEventAndQuote(quoteFields: List<String>): Flow<ConnectionState> =
        merge(observeEvent(quoteFields), observeQuote())

    private fun observeEvent(quotes: List<String>?): Flow<ConnectionState> =
        quoteScarletService.observeEvent().map { event ->
            when (event) {
                is WebSocket.Event.OnConnectionOpened<*> -> {
                    quoteScarletService.sendSubscribe(createSubscriptionRequest(quotes))
                    ConnectionState.Connected
                }

                is WebSocket.Event.OnMessageReceived -> ConnectionState.Connected
                else -> ConnectionState.Disconnected
            }
        }

    private fun createSubscriptionRequest(quotes: List<String>?): String {
        val quotesList =
            quotes.takeIf { !it.isNullOrEmpty() } ?: emptyList()
        val request = listOf("realtimeQuotes", quotesList)
        return Gson().toJson(request)
    }

    private fun observeQuote(): Flow<ConnectionState> = tickerUpdates.map { quoteList ->
        ConnectionState.Success(data = quoteList)
    }

    suspend fun onAnimationEnd(ticker: String) = updateMutex.withLock {
        val currentList = _tickerUpdates.value.toMutableList()
        val index = currentList.indexOfFirst { it.ticker == ticker }
        if (index != -1) {
            currentList[index] = currentList[index].copy(shouldAnimatePercentageChange = false)
            _tickerUpdates.value = currentList
        }
    }
}