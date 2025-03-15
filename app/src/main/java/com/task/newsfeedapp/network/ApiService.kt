package com.task.newsfeedapp.network

import com.task.newsfeedapp.model.ArticleResponse
import com.task.newsfeedapp.utils.Utils
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("svc/search/v2/articlesearch.json")
    suspend fun getArticles(
        @Query("api-key") apiKey: String,
        @Query("page") page: Int,
    ): Response<ArticleResponse>

    object NetworkClient {
        private val retrofit: Retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(Utils.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        val apiService: ApiService by lazy {
            retrofit.create(ApiService::class.java)
        }
    }
}