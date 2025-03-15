package com.task.newsfeedapp.resource

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.task.newsfeedapp.model.ArticleResponse
import com.task.newsfeedapp.network.ApiService
import com.task.newsfeedapp.utils.Utils

class ArticlePagingSource(
    private val apiService: ApiService
) : PagingSource<Int, ArticleResponse.Legacy.Multimedia.Headline.Keywords.Person.Byline.Docs>() {

    override fun getRefreshKey(state: PagingState<Int, ArticleResponse.Legacy.Multimedia.Headline.Keywords.Person.Byline.Docs>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticleResponse.Legacy.Multimedia.Headline.Keywords.Person.Byline.Docs> {
        return try {
            val page = params.key ?: 1
            val response = apiService.getArticles(page = page, apiKey = Utils.api_key)
            val articles = response.body()?.response?.docs.orEmpty()

            LoadResult.Page(
                data = articles,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (articles.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            Log.e("ArticlePagingSource", "Error loading data: ", e)
            LoadResult.Error(e)
        }
    }
}
