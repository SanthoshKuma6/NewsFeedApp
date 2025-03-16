package com.task.newsfeedapp.mvvm.repository

import com.task.newsfeedapp.model.ArticleResponse
import com.task.newsfeedapp.network.ApiService

class ArticleRepo(private val apiService: ApiService) {
    suspend fun getArticle(page: Int,key: String, ): retrofit2.Response<ArticleResponse> {
        return apiService.getArticles(page,key)
    }
}