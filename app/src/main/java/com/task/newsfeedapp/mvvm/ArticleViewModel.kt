package com.task.newsfeedapp.mvvm

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.task.newsfeedapp.model.ArticleResponse
import com.task.newsfeedapp.network.ApiService.NetworkClient.apiService
import com.task.newsfeedapp.resource.ArticlePagingSource
import com.task.newsfeedapp.utils.NetworkMonitor
import com.task.newsfeedapp.utils.Utils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ArticleViewModel(private val articleRepo: ArticleRepo, val context: Context) : ViewModel() {

    val articlePager = Pager(
        config = PagingConfig(
            pageSize = 10, // Adjust based on API
            enablePlaceholders = false
        ),
        pagingSourceFactory = { ArticlePagingSource(apiService) }
    ).flow.cachedIn(viewModelScope)

    val networkMonitor = NetworkMonitor(context)
    val _isConnected = MutableStateFlow(false)
    val isConnected: StateFlow<Boolean> = _isConnected
    private val _articleState =
        MutableStateFlow<Response<ArticleResponse>>(Response.Loading(false))
    val articleState: StateFlow<Response<ArticleResponse>> = _articleState

    init {
        viewModelScope.launch {
            networkMonitor.isConnected.collectLatest { connected ->
                _isConnected.value = connected
                if (connected) {
                    getArticleList(Utils.api_key, 1)
                }

            }
        }

    }

    fun getArticleList(key: String, page: Int) {
        viewModelScope.launch {
            if (!_isConnected.value) {
                _articleState.value = Response.Error("You're offline. Showing cached data.")
                return@launch
            }

            _articleState.value = Response.Loading(true)
            try {
                val data = articleRepo.getArticle(key, page)
                if (data.isSuccessful) {
                    Log.d("TAG", "isSuccessful: ${data.isSuccessful}")
                    _articleState.value = Response.Loading(false)
                    _articleState.value = Response.Success(data.body())
                } else {
                    _articleState.value = Response.Loading(false)
                    _articleState.value = Response.Error(data.message())
                    Log.d("TAG", "Error: ${data.isSuccessful}")

                }
            } catch (e: Exception) {
                Log.d("TAG", "Exception: ${e.message}")
            }

        }
    }

}