package com.ccwo.flowaccounttest.api

import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface RepoApi {
    @GET("repositories")
    fun getCoins(@Query("q") q: String,@Query("page") page: Int,@Query("per_page") per_page: Int): Call<RepoApiResponse>

    companion object {
        private const val BASE_URL = "https://api.github.com/search/"
        fun create(): RepoApi {
            val client = OkHttpClient.Builder().build()
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RepoApi::class.java)
        }
    }
}