package com.dicoding.parsingjson.network

import com.dicoding.parsingjson.model.ResponseUser
import com.example.teamapp.model.ResponseUserGithub
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    // Mengambil daftar pengguna GitHub
    @GET("users")
    suspend fun getUserGithub(): Response<ResponseUserGithub>
}
