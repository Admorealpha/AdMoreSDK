package com.alpha.admoresdk.di

import com.alpha.admoresdk.BuildConfig
import com.alpha.admoresdk.core.network.ApiService
import com.alpha.admoresdk.core.network.NetworkMonitor
import com.alpha.admoresdk.core.network.RetryInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {

    single<HttpLoggingInterceptor> {
        HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
    }

    single { RetryInterceptor() }

    single<OkHttpClient> {
        OkHttpClient.Builder().apply {
            // Add retry interceptor first
            addInterceptor(get<RetryInterceptor>())

            // Add logging interceptor for debugging
//            if (BuildConfig.DEBUG) {
//                addInterceptor(get<HttpLoggingInterceptor>())
//            }

            // Timeouts
            connectTimeout(30L, TimeUnit.SECONDS)
            readTimeout(30L, TimeUnit.SECONDS)
            writeTimeout(30L, TimeUnit.SECONDS)

            // Connection pooling
            connectionPool(okhttp3.ConnectionPool(5, 5, TimeUnit.MINUTES))

            // Disable automatic retries
            retryOnConnectionFailure(false)
        }.build()
    }

    single<Retrofit> {
        // For HTTPS URLs, ensure proper base URL construction
        val baseUrl = when {
            BuildConfig.host.startsWith("https://") || BuildConfig.host.startsWith("http://") -> {
                if (BuildConfig.host.endsWith("/")) BuildConfig.host else "${BuildConfig.host}/"
            }
            else -> {
                // Default to HTTPS for security
                "https://${BuildConfig.host}/"
            }
        }

        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<ApiService> {
        get<Retrofit>().create(ApiService::class.java)
    }

    single { NetworkMonitor(androidContext()) }
}