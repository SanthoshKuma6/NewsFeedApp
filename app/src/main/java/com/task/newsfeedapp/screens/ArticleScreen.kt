package com.task.newsfeedapp.screens

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.task.newsfeedapp.component.LoaderWithText
import com.task.newsfeedapp.dao.RoomDao
import com.task.newsfeedapp.factory.RoomViewModelFactory
import com.task.newsfeedapp.factory.ViewModelFactory
import com.task.newsfeedapp.model.RoomModel
import com.task.newsfeedapp.mvvm.ArticleRepo
import com.task.newsfeedapp.mvvm.ArticleViewModel
import com.task.newsfeedapp.mvvm.Response
import com.task.newsfeedapp.mvvm.RoomRepository
import com.task.newsfeedapp.mvvm.RoomViewModel
import com.task.newsfeedapp.network.ApiService
import com.task.newsfeedapp.utils.Utils.api_key


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun ArticleScreen(navController: NavController) {
    val context = LocalContext.current
    var isLoading by remember { mutableStateOf(false) }
    var apiHit by remember { mutableStateOf(false) }
    var roomHit by remember { mutableStateOf(false) }
    var page = 1
    var wasOffline by remember { mutableStateOf(false) }

    val viewModel: ArticleViewModel = viewModel(
        factory = ViewModelFactory(
            ArticleRepo(ApiService.NetworkClient.apiService), context
        )
    )
    val repo = remember { RoomRepository(RoomDao.getDatabase(context)) }
    val factory = remember { RoomViewModelFactory(repo) }
    val roomViewModel: RoomViewModel = viewModel(factory = factory)
    val isConnected by viewModel.isConnected.collectAsStateWithLifecycle()
    val cachedArticles by roomViewModel.articles.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        if (!isConnected){
            if (wasOffline){
                Toast.makeText(context, "You're back online. Refreshing data.", Toast.LENGTH_SHORT).show()
                viewModel.getArticleList(api_key, page)
            }
            wasOffline = false
        } else {
            wasOffline=true
            roomViewModel.getList()
        }

    }



    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column {
            if (!isConnected) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Red)
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "You're offline. Showing cached data.",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }


        Spacer(modifier = Modifier.heightIn(min = 60.dp))
        val movieState by viewModel.articleState.collectAsStateWithLifecycle()
        when (val result = movieState) {
            is Response.Loading -> {
                isLoading = true
                LoaderWithText()
            }

            is Response.Success -> {
                isLoading = false
                val roomDataList: List<RoomModel> = result.data!!.response!!.docs.map { doc ->
                    RoomModel(
                        webUrl = doc.webUrl,
                        abstract = doc.abstract,
                        snippet = doc.snippet,
                        leadParagraph = doc.leadParagraph,
                        source = doc.source,
                        pubDate = doc.pubDate,
                        documentType = doc.documentType,
                        newsDesk = doc.newsDesk,
                        sectionName = doc.sectionName,
                        typeOfMaterial = doc.typeOfMaterial,
                        uri = doc.uri,
                        wordCount = doc.wordCount
                    )

                }
                InsertRoomData(roomDataList)

                result.data.let { article ->
                    ArticleList(listOf(article), navController)
                    Log.d("TAG", "ArticleScreen: $article")

                }

            }

            is Response.Error -> {
                isLoading = false
//                ArticleList(cachedArticles,navController)
                Log.d("TAG", "errorMessage: ${result.errorMessage}")
                Toast.makeText(context, "${result.errorMessage}", Toast.LENGTH_LONG).show()
            }
        }

    }
}


@Composable
fun InsertRoomData(roomDataList: List<RoomModel>) {
    val context = LocalContext.current
    val repo = remember { RoomRepository(RoomDao.getDatabase(context)) }
    val factory = remember { RoomViewModelFactory(repo) }
    val roomViewModel: RoomViewModel = viewModel(factory = factory)

    LaunchedEffect(Unit) {
        roomViewModel.insert(roomDataList)
        roomViewModel.getList()
    }
}

