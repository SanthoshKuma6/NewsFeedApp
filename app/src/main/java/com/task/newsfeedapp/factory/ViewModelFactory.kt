package com.task.newsfeedapp.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.task.newsfeedapp.mvvm.ArticleRepo
import com.task.newsfeedapp.mvvm.ArticleViewModel

class ViewModelFactory(private val articleRepo: ArticleRepo,private val context: Context): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ArticleViewModel::class.java)) {
            return ArticleViewModel(articleRepo,context) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}