package com.task.newsfeedapp.mvvm

import com.task.newsfeedapp.model.ArticleResponse
import com.task.newsfeedapp.network.ApiService

class ArticleRepo(private val apiService: ApiService) {
    suspend fun getArticle(key: String, page: Int): retrofit2.Response<ArticleResponse> {
        return apiService.getArticles(key, page)
    }
}