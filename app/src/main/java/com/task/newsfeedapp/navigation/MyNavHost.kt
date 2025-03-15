package com.task.newsfeedapp.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.gson.Gson
import com.task.newsfeedapp.model.ArticleResponse
import com.task.newsfeedapp.screens.ArticleDetailScreen
import com.task.newsfeedapp.screens.ArticleListScreen
import com.task.newsfeedapp.screens.ArticleScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MyNavHost() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "ArticleScreen") {
        composable("ArticleScreen") {
            ArticleScreen(navController)
        }
//        composable("ArticleListScreen") {
//            ArticleListScreen(navController = navController)
//        }
//        composable("ArticleDetailScreen/{articleId}") { backStackEntry ->
//            val articleId = backStackEntry.arguments?.getString("articleId")
//            ArticleDetailScreen(navController, articleId)
//        }

        composable(
            "ArticleDetailScreen/{articleJson}",
            arguments = listOf(navArgument("articleJson") { type = NavType.StringType })
        ) { backStackEntry ->
            val json = backStackEntry.arguments?.getString("articleJson")
            val article  = json?.let { Gson().fromJson(it, ArticleResponse.Legacy.Multimedia.Headline.Keywords.Person.Byline.Docs::class.java) }
            ArticleDetailScreen(navController,article)
        }

    }
}