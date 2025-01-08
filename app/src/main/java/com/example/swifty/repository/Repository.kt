package com.example.swifty.repository

import com.example.swifty.data.model.User
import com.example.swifty.data.network.ApiService
import retrofit2.HttpException
import java.io.IOException

/**
 * Repository class responsible for managing API interactions and token handling.
 *
 * @param clientId The client ID for authentication.
 * @param clientSecret The client secret for authentication.
 */
class Repository(
    private val clientId: String,
    private val clientSecret: String
) {
    private var accessToken: String? = null;
    private var tokenExpirationTime: Long = 0L; // In seconds since Epoch.

    /**
     * Retrieves an access token if required.
     *
     * If the current token is missing or expired, a new token is fetched from the API.
     * Updates the `ApiService` with the new token for future requests.
     *
     * @return The current valid access token.
     * @throws NetworkException if there is a network issue while fetching the token.
     * @throws ApiException if the API returns an error response.
     */
    suspend fun getAccessToken(): String {
        val currentTime = System.currentTimeMillis() / 1000; // Current time in seconds
        if (accessToken.isNullOrEmpty() || currentTime >= tokenExpirationTime) {
            try {
                val response = ApiService.api.getAccessToken(
                    clientId = clientId,
                    clientSecret = clientSecret
                );
                accessToken = response.access_token;
                tokenExpirationTime = currentTime + response.expires_in; // Set expiration time
                ApiService.setAccessToken(accessToken!!); // Update token in ApiService
            } catch (e: IOException) {
                throw NetworkException("Network error: Unable to fetch access token");
            } catch (e: HttpException) {
                throw ApiException("API error: ${e.response()?.message()}");
            }
        }
        return accessToken!!
    }

    /**
     * Fetches user information by login.
     *
     * Ensures a valid access token is available before making the API call.
     * Handles errors such as network issues or API errors (e.g., user not found).
     *
     * @param login The login of the user to fetch.
     * @return The `User` object containing user details.
     * @throws UserNotFoundException if the user does not exist.
     * @throws NetworkException if there is a network issue while fetching user data.
     * @throws ApiException if the API returns an error response.
     */
    suspend fun getUser(login: String): User {
        try {
            // Ensure a valid access token is available
            getAccessToken();

            // Fetch user data from the API
            return ApiService.api.getUser(login);
        } catch (e: HttpException) {
            if (e.code() == 404) {
                throw UserNotFoundException("User with login $login not found");
            } else {
                throw ApiException("API error: ${e.response()?.message()}");
            }
        } catch (e: IOException) {
            throw NetworkException("Network error: Unable to fetch user data");
        }
    }
}

/**
 * Exception for network-related errors.
 */
class NetworkException(message: String) : Exception(message);

/**
 * Exception for errors when a user is not found.
 */
class UserNotFoundException(message: String) : Exception(message);

/**
 * Exception for generic API errors.
 */
class ApiException(message: String) : Exception(message);
