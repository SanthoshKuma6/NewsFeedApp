package com.task.newsfeedapp.mvvm.repository

import com.task.newsfeedapp.dao.RoomDao
import com.task.newsfeedapp.model.ArticleResponse
import com.task.newsfeedapp.model.RoomModel
import com.task.newsfeedapp.network.ApiService
import kotlinx.coroutines.flow.Flow

class ArticleRepo(private val apiService: ApiService, private val roomDao: RoomDao) {
    suspend fun getArticle(page: Int,key: String, ): retrofit2.Response<ArticleResponse> {
        return apiService.getArticles(page,key)
    }

  suspend  fun getCachedArticles(): List<RoomModel> {
    return roomDao.roomInterface().getArticle()
    }

}