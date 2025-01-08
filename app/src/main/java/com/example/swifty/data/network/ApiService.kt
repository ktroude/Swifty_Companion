package com.example.swifty.data.network

import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * ApiService is a singleton that provides access to the API using Retrofit.
 * It handles the configuration of HTTP clients, interceptors, and access tokens.
 */
object ApiService {

    /**
     * The base URL for the API.
     */
    private const val BASE_URL = "https://api.intra.42.fr/"

    /**
     * The access token used for authenticated API requests.
     */
    private var accessToken: String? = null

    /**
     * Interceptor that adds the access token to API requests, if available.
     */
    private val authInterceptor = Interceptor { chain ->
        val request = chain.request().newBuilder()
        if (!accessToken.isNullOrEmpty()) {
            request.addHeader("Authorization", "Bearer $accessToken")
        }
        chain.proceed(request.build())
    }

    /**
     * Interceptor that logs HTTP request and response details for debugging purposes.
     */
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    /**
     * HTTP client configured with interceptors for authentication and logging.
     */
    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .addInterceptor(loggingInterceptor)
        .build()

    /**
     * Retrofit instance configured with the base URL, HTTP client, and Gson converter.
     *
     * Retrofit is a library that simplifies making HTTP requests and parsing responses.
     * - It uses the base URL to define API endpoints.
     * - The HTTP client handles authentication and logging through interceptors.
     * - GsonConverterFactory parses JSON responses into Kotlin objects.
     *
     * This instance is used to implement the [ApiInterface] for interacting with the API.
     */
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(httpClient)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .build()

    /**
     * An instance of the API interface for making network requests.
     */
    val api: ApiInterface = retrofit.create(ApiInterface::class.java)

    /**
     * Updates the access token used for authenticated requests.
     *
     * @param token The new access token to set.
     */
    fun setAccessToken(token: String) {
        accessToken = token
    }
}

