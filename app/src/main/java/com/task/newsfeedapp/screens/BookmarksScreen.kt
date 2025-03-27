package com.task.newsfeedapp.screens

import android.annotation.SuppressLint
import android.net.Uri
import android.util.Base64
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.google.gson.Gson
import com.task.newsfeedapp.component.CircularLoader
import com.task.newsfeedapp.component.formatTime
import com.task.newsfeedapp.dao.RoomDao
import com.task.newsfeedapp.factory.RoomViewModelFactory
import com.task.newsfeedapp.model.RoomModel
import com.task.newsfeedapp.mvvm.repository.RoomRepository
import com.task.newsfeedapp.mvvm.viewmodel.RoomViewModel
import com.task.newsfeedapp.resource.RoomResource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Dispatcher

/**
 * SANTHOSH
 */
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun BookmarksScreen(navController: NavController) {
    val context = LocalContext.current
    val repo = remember { RoomRepository(RoomDao.getDatabase(context)) }
    val factory = remember { RoomViewModelFactory(repo) }
    val roomViewModel: RoomViewModel = viewModel(factory = factory)

    LaunchedEffect(Unit) {
        roomViewModel.getList()

    }


    val resource = roomViewModel.articles.collectAsState(initial = RoomResource.Loading())
    when (val result = resource.value) {
        is RoomResource.Loading -> {
            Log.d("room", "ArticleScreen: Loading")
            CircularLoader()

        }

        is RoomResource.Success -> {
            GetShowRoomData(result.data, navController)
            Log.d("room", "ArticleScreen: Success")

        }

        is RoomResource.Error -> {
            Log.d("room", "ArticleScreen: Error")
        }
    }
}


@Composable
fun GetShowRoomData(
    data: List<RoomModel>,
    navController: NavController
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(data) {
            val roomJson = Uri.encode(Gson().toJson(it))
            Log.d("bookmark", "roomList: $roomJson")
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .clickable {
                        navController.navigate("RoomDetailedScreen/$roomJson")
                    }) {
                Text(
                    text = it.newsDesk.toString(),
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = it.snippet.toString(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(8.dp))
                Log.d(
                    "RoomImg", "BookmarksScreen: \"https://www.nytimes.com/${it.uri}"
                )
                Image(
                    painter = rememberAsyncImagePainter("https://www.nytimes.com/${it.imageUrl}"),
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
                    text = formatTime(it.pubDate),
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Black
                )
            }

        }


    }
}

