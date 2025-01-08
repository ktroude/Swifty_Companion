package com.example.swifty.data.network

import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiService {
    private const val BASE_URL = "https://api.intra.42.fr/"

    // Variable pour stocker le token d'accès
    private var accessToken: String? = null

    // Intercepteur pour ajouter le token d'accès dans les requêtes
    private val authInterceptor = Interceptor { chain ->
        val request = chain.request().newBuilder()
        if (!accessToken.isNullOrEmpty()) {
            request.addHeader("Authorization", "Bearer $accessToken")
        }
        chain.proceed(request.build())
    }

    // Intercepteur pour afficher les logs HTTP
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .addInterceptor(loggingInterceptor)
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(httpClient)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .build()

    // Interface de l'API 42
    val api: ApiInterface = retrofit.create(ApiInterface::class.java)

    // Setter pour mettre à jour le token d'accès
    fun setAccessToken(token: String) {
        accessToken = token
    }
}

