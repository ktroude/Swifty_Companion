package com.example.swifty.data.network

import com.example.swifty.data.AccessTokenResponse
import com.example.swifty.data.model.User
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * ApiInterface defines the API endpoints for interacting with the external service.
 * It includes methods to fetch an access token and retrieve user data.
 */
interface ApiInterface {

    /**
     * Fetches an OAuth2 access token using client credentials.
     *
     * @param grantType The type of grant to use, default is "client_credentials".
     * @param clientId The client ID provided by the API.
     * @param clientSecret The client secret provided by the API.
     * @return An [AccessTokenResponse] containing the access token and related metadata.
     */
    @FormUrlEncoded
    @POST("oauth/token")
    suspend fun getAccessToken(
        @Field("grant_type") grantType: String = "client_credentials",
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String
    ): AccessTokenResponse;

    /**
     * Retrieves user information based on their login identifier.
     *
     * @param login The login identifier of the user.
     * @return A [User] object containing the user's details.
     */
    @GET("v2/users/{login}")
    suspend fun getUser(
        @Path("login") login: String
    ): User;
}
