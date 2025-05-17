package com.task.newsfeedapp.base.network

import com.task.newsfeedapp.model.ArticleResponse
import retrofit2.http.Body
import retrofit2.http.POST
import javax.inject.Singleton

@Singleton
interface INetworkService {
    @POST(Endpoints.getAuthentication)
    suspend fun getAuthentication(
        @Body requestBody: ArticleResponse
    ): ArticleResponse

}