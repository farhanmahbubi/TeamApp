package com.example.teamapp.network

import com.example.teamapp.model.ResponseUserGithub
import com.google.firebase.BuildConfig
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface GithubService {
    @GET("users")
    suspend fun getUserGithub(): Response<ResponseUserGithub>
}


//    @JvmSuppressWildcards
//    @GET("users/{username}")
//    suspend fun getDetailUserGithub(
//        @Path("username") username: String,
//        @Header("Authorization")
//        authorization: String = BuildConfig.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
//    ): ResponseDetailUser
//
//    @JvmSuppressWildcards
//    @GET("/users/{username}/followers")
//    suspend fun getFollowersUserGithub(
//        @Path("username") username: String,
//        @Header("Authorization")
//        authorization: String = BuildConfig.TOKEN
//    ): MutableList<ResponseUserGithub.Item>
//
//    @JvmSuppressWildcards
//    @GET("/users/{username}/following")
//    suspend fun getFollowingUserGithub(
//        @Path("username") username: String,
//        @Header("Authorization")
//        authorization: String = BuildConfig.TOKEN
//    ): MutableList<ResponseUserGithub.Item>
//
//    @JvmSuppressWildcards
//    @GET("search/users")
//    suspend fun searchUserGithub(
//        @QueryMap params: Map<String, Any>,
//        @Header("Authorization")
//        authorization: String = BuildConfig.TOKEN
//    ): ResponseUserGithub

