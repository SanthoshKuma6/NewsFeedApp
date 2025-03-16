package com.task.newsfeedapp.network

import androidx.room.Dao
import androidx.room.Insert
import com.task.newsfeedapp.model.ArticleResponse
import com.task.newsfeedapp.model.RoomModel
import com.task.newsfeedapp.utils.Utils
import okhttp3.Dns
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * SANTHOSH
 */
interface ApiService {
    @GET("svc/search/v2/articlesearch.json")
    suspend fun getArticles(
        @Query("page") page: Int,
        @Query("api-key") apiKey: String,

        ): Response<ArticleResponse>


}


@Dao
interface RoomInterface {
    @Insert
    fun insertArticle(movie: List<RoomModel>)

    @androidx.room.Query("SELECT * FROM articles ")
    fun getArticle(): List<RoomModel>

}