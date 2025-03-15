package com.task.newsfeedapp.screens

import android.annotation.SuppressLint
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberAsyncImagePainter
import com.google.gson.Gson
import com.task.newsfeedapp.component.AnimatedLoader
import com.task.newsfeedapp.component.CircularLoader
import com.task.newsfeedapp.component.LinearLoader
import com.task.newsfeedapp.component.formatTime
import com.task.newsfeedapp.dao.RoomDao
import com.task.newsfeedapp.factory.RoomViewModelFactory
import com.task.newsfeedapp.factory.ViewModelFactory
import com.task.newsfeedapp.model.ArticleResponse
import com.task.newsfeedapp.mvvm.ArticleRepo
import com.task.newsfeedapp.mvvm.ArticleViewModel
import com.task.newsfeedapp.mvvm.RoomRepository
import com.task.newsfeedapp.mvvm.RoomViewModel
import com.task.newsfeedapp.network.ApiService
import com.task.newsfeedapp.utils.Utils
import com.task.newsfeedapp.resource.RoomResource


@Composable
fun ArticleListScreen(navController: NavController) {
    val articles = listOf<ArticleResponse>() // Fetch articles here
    ArticleList(articles, navController)
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ArticleList(article: List<ArticleResponse>,navController: NavController) {

    val tabIndex = remember { mutableStateOf(0) }
    val searchQuery = remember { mutableStateOf("") }
    val filteredArticles = article.filter {
        article.toString().contains(searchQuery.value, ignoreCase = true)

    }

    Scaffold(
        topBar = {
            Column {
                Spacer(Modifier.height(20.dp))
                TextField(
                    value = searchQuery.value,
                    onValueChange = { searchQuery.value = it },
                    label = { Text("Search...") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Search Icon",
                            tint = Color.Gray
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .clip(RoundedCornerShape(20.dp)),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color(0xFFFFFFFF),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    singleLine = true
                )
                NewsFeedScreen(article = filteredArticles,navController)
            }
        },
        content = {
            Log.d("TAG", "ArticleList: $article")
        })
}

@Composable
fun NewsFeedScreen(article: List<ArticleResponse>,navController: NavController) {
    val tabIndex = remember { mutableStateOf(0) }
    Column(modifier = Modifier.fillMaxSize()) {
        TabLayout(tabIndex)
        TabContent(tabIndex.value, article,navController)
    }
}

@Composable
fun TabLayout(tabIndex: MutableState<Int>) {
    val tabs = listOf("Feeds", "Bookmarks")
    TabRow(
        modifier = Modifier.fillMaxWidth(),
        selectedTabIndex = tabIndex.value,
        containerColor = Color.White,
        indicator = { tabPositions ->
            SecondaryIndicator(
                modifier = Modifier.tabIndicatorOffset(tabPositions[tabIndex.value]),
                color = Color.Black
            )
        }
    ) {
        tabs.forEachIndexed { index, title ->
            Tab(
                text = {
                    Text(
                        text = title,
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = Color.Black
                        )
                    )
                },
                selected = tabIndex.value == index,
                onClick = { tabIndex.value = index }
            )
        }
    }
}

@Composable
fun TabContent(tabIndex: Int, article: List<ArticleResponse>,navController: NavController) {
    when (tabIndex) {
        0 -> FeedsScreen(navController,article)
        1 -> BookmarksScreen(navController)
    }
}

@Composable
fun FeedsScreen(navController: NavController, article: List<ArticleResponse>) {
    val context = LocalContext.current
    val viewModel: ArticleViewModel = viewModel(
        factory = ViewModelFactory(
            ArticleRepo(ApiService.NetworkClient.apiService), context
        )
    )
    val articleItems = viewModel.articlePager.collectAsLazyPagingItems()

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(articleItems.itemCount) { articleItem ->
            Log.d("TAG", "FeedsScreen: $articleItem")
//                for (i in docs) {
            val docs = articleItems[articleItem]
            val articleJson = Uri.encode(Gson().toJson(docs))
            val imageUrl = docs?.multimedia?.firstOrNull()?.url.let { Utils.BASE_URL + it }
            Log.d("TAG", "FeedsScreen: $imageUrl")

            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .clickable {
//                                navController.navigate("ArticleDetailScreen")
                        navController.navigate("ArticleDetailScreen/$articleJson")

                    }
            ) {
                Text(
                    text = docs?.newsDesk.toString(),
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = docs?.snippet.toString(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(8.dp))

                Image(
                    painter = rememberAsyncImagePainter(imageUrl),
                    contentDescription = "Feed Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.LightGray),
                    contentScale = ContentScale.Crop
                )

                Spacer(Modifier.heightIn(10.dp))

                Text(
                    text = formatTime(docs?.pubDate),
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Black
                )

            }
        }
        articleItems.apply {
            when{
                loadState.refresh is LoadState.Loading ->{
                    item { LinearLoader() }
                }
                loadState.append is LoadState.Loading -> {
                    item { LinearLoader() }
                }
                loadState.append is LoadState.Error -> {
                    item {
                        Text(
                            text = "Error loading more articles",
                            color = Color.Red,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}



    @SuppressLint("CoroutineCreationDuringComposition")
    @Composable
    fun BookmarksScreen(navController: NavController) {
        val context = LocalContext.current
        val repo = remember { RoomRepository(RoomDao.getDatabase(context)) }
        val factory = remember { RoomViewModelFactory(repo) }
        val roomViewModel: RoomViewModel = viewModel(factory = factory)

        val resource = roomViewModel.articles.collectAsState(initial = RoomResource.Loading())
        when (val result = resource.value) {
            is RoomResource.Loading -> {
                Log.d("room", "ArticleScreen: Loading")
                CircularLoader()

            }

            is RoomResource.Success -> {
                Log.d("room", "ArticleScreen: Success")
                val articles = result.data
                Log.d("TAG", "BookmarksScreen: $articles")
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(articles) { roomModel ->
                        Log.d("BookmarksScreen", "BookmarksScreen: $roomModel")
                        Column(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = roomModel.newsDesk.toString(),
                                style = MaterialTheme.typography.titleLarge,
                                color = Color.Black,
                                fontWeight = FontWeight.Bold
                            )

                            Text(
                                text = roomModel.snippet.toString(),
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Black
                            )

                            Spacer(modifier = Modifier.height(8.dp))
                            Image(
                                painter = rememberAsyncImagePainter(roomModel.uri),
                                contentDescription = "Feed Image",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color.LightGray),
                                contentScale = ContentScale.Crop
                            )

                            Spacer(Modifier.heightIn(10.dp))

                            Text(
                                text = formatTime(roomModel.pubDate),
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Black
                            )
                        }
                    }
                }
            }

            is RoomResource.Error -> {
                Log.d("room", "ArticleScreen: Error")
            }
        }
    }


