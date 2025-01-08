package com.example.swifty.repository

import com.example.swifty.data.model.User
import com.example.swifty.data.network.ApiService
import retrofit2.HttpException
import java.io.IOException

class Repository(
    private val clientId: String,
    private val clientSecret: String
) {
    private var accessToken: String? = null
    private var tokenExpirationTime: Long = 0L // En secondes depuis Epoch

    /**
     * Récupère un token d'accès si nécessaire.
     */
    suspend fun getAccessToken(): String {
        val currentTime = System.currentTimeMillis() / 1000 // Temps actuel en secondes
        if (accessToken.isNullOrEmpty() || currentTime >= tokenExpirationTime) {
            try {
                val response = ApiService.api.getAccessToken(
                    clientId = clientId,
                    clientSecret = clientSecret
                )
                accessToken = response.access_token
                tokenExpirationTime = currentTime + response.expires_in // Définit l'heure d'expiration
                ApiService.setAccessToken(accessToken!!) // Met à jour le token dans ApiService
            } catch (e: IOException) {
                throw NetworkException("Network error: Unable to fetch access token")
            } catch (e: HttpException) {
                throw ApiException("API error: ${e.response()?.message()}")
            }
        }
        return accessToken!!
    }

    /**
     * Récupère les informations d'un utilisateur.
     */
    suspend fun getUser(login: String): User {
        try {
            // Récupère le token d'accès si nécessaire
            getAccessToken()

            // Appelle l'API pour récupérer les données utilisateur
            return ApiService.api.getUser(login)
        } catch (e: HttpException) {
            if (e.code() == 404) {
                throw UserNotFoundException("User with login $login not found")
            } else {
                throw ApiException("API error: ${e.response()?.message()}")
            }
        } catch (e: IOException) {
            throw NetworkException("Network error: Unable to fetch user data")
        }
    }
}


// Exceptions spécifiques
class NetworkException(message: String) : Exception(message)
class UserNotFoundException(message: String) : Exception(message)
class ApiException(message: String) : Exception(message)
