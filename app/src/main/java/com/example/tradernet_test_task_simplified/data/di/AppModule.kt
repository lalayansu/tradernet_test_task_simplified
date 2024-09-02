package com.example.tradernet_test_task_simplified.data.di

import android.app.Application
import com.example.tradernet_test_task_simplified.data.di.scarlet.BACKOFF_STRATEGY
import com.example.tradernet_test_task_simplified.data.di.scarlet.FlowStreamAdapter
import com.example.tradernet_test_task_simplified.data.di.scarlet.StringMessageAdapterFactory
import com.example.tradernet_test_task_simplified.data.di.scarlet.createAppForegroundLifecycle
import com.example.tradernet_test_task_simplified.data.repo.QuoteRepository
import com.example.tradernet_test_task_simplified.data.service.QuoteScarletService
import com.example.tradernet_test_task_simplified.data.service.QuoteService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.tinder.scarlet.Scarlet
import com.tinder.scarlet.lifecycle.android.BuildConfig
import com.tinder.scarlet.messageadapter.gson.GsonMessageAdapter
import com.tinder.scarlet.websocket.okhttp.newWebSocketFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    @Named("gson")
    fun provideGson(): Gson = GsonBuilder().create()

    @OkHttpClientWithoutAuth
    @Provides
    @Singleton
    fun provideOkHttpClientWithoutAuth(): OkHttpClient {
        val logger = HttpLoggingInterceptor()
            .setLevel(
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
                else HttpLoggingInterceptor.Level.NONE
            )

        return OkHttpClient.Builder()
            .addInterceptor(logger)
            .build()
    }

    @RetrofitClientWithoutAuth
    @Singleton
    @Provides
    fun providesRetrofitWithoutAuth(
        @OkHttpClientWithoutAuth client: OkHttpClient,
        @Named("gson") gson: Gson,
    ): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(gson))
        .baseUrl("https://tradernet.com/")
        .client(client)
        .build()

    @Provides
    @Singleton
    fun provideQuoteService(
        @RetrofitClientWithoutAuth retrofit: Retrofit
    ): QuoteService {
        return retrofit.create(QuoteService::class.java)
    }

    @Provides
    @Singleton
    fun provideQuoteRepository(
        quoteService: QuoteService,
        quoteScarletService: QuoteScarletService
    ): QuoteRepository {
        return QuoteRepository(quoteService, quoteScarletService)
    }
}

@Module
@InstallIn(SingletonComponent::class)
object ScarletModule {

    @Provides
    @Singleton
    fun provideQuoteScarletService(
        @OkHttpClientWithoutAuth client: OkHttpClient,
        @Named("gson") gson: Gson,
        application: Application
    ): QuoteScarletService {
        val scarlet = Scarlet.Builder()
            .webSocketFactory(client.newWebSocketFactory("wss://wss.tradernet.com"))
            .addMessageAdapterFactory(StringMessageAdapterFactory())
            .addMessageAdapterFactory(GsonMessageAdapter.Factory(gson))
            .addStreamAdapterFactory(FlowStreamAdapter.Factory())
            .backoffStrategy(BACKOFF_STRATEGY)
            .lifecycle(createAppForegroundLifecycle(application))
            .build()

        return scarlet.create(QuoteScarletService::class.java)
    }
}