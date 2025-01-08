package com.example.swifty.data.network

import com.example.swifty.data.AccessTokenResponse
import com.example.swifty.data.model.User
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiInterface {
    @FormUrlEncoded
    @POST("oauth/token")
    suspend fun getAccessToken(
        @Field("grant_type") grantType: String = "client_credentials",
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String
    ): AccessTokenResponse

    @GET("v2/users/{login}")
    suspend fun getUser(
        @Path("login") login: String
    ): User
}
