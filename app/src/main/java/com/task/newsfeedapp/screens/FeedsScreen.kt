package com.task.newsfeedapp.screens

import android.annotation.SuppressLint
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.google.gson.Gson
import com.task.newsfeedapp.component.LoaderWithText
import com.task.newsfeedapp.component.formatTime
import com.task.newsfeedapp.dao.RoomDao
import com.task.newsfeedapp.factory.RoomViewModelFactory
import com.task.newsfeedapp.factory.ViewModelFactory
import com.task.newsfeedapp.model.ArticleResponse
import com.task.newsfeedapp.model.RoomModel
import com.task.newsfeedapp.mvvm.repository.ArticleRepo
import com.task.newsfeedapp.mvvm.repository.RoomRepository
import com.task.newsfeedapp.mvvm.viewmodel.ArticleViewModel
import com.task.newsfeedapp.mvvm.viewmodel.RoomViewModel
import com.task.newsfeedapp.network.NetworkClient
import com.task.newsfeedapp.resource.Response
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * SANTHOSH
 */

@Composable
fun FeedsScreen(navController: NavController) {
    val context = LocalContext.current
    var isLoading by remember { mutableStateOf(false) }
    val viewModel: ArticleViewModel = viewModel(
        factory = ViewModelFactory(
            ArticleRepo(NetworkClient.apiService, RoomDao.getDatabase(context)), context
        )
    )
    val isConnected by viewModel.isConnected.collectAsStateWithLifecycle()
    Surface(
        modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
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
        val articleState by viewModel.articleState.collectAsStateWithLifecycle()
        when (val result = articleState) {
            is Response.Loading -> {
                isLoading = true
                LoaderWithText()
            }

            is Response.Success -> {
                isLoading = false


                val roomDataList: List<RoomModel> = result.data!!.response!!.docs.map { doc ->


                    val imageUrl = doc.multimedia.url ?: ""
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
                        wordCount = doc.wordCount,
                        imageUrl = imageUrl
                    )


                }

                InsertRoomData(roomDataList)

                result.data.let { article ->
                    Log.d("TAG", "Success Result: $article")
                    FeedScreenResponseUI(result.data.response!!.docs, navController)

                }

            }

            is Response.Error -> {
                isLoading = false
                Log.d("TAG", "errorMessage: ${result.errorMessage}")
                Toast.makeText(context, "${result.errorMessage}", Toast.LENGTH_LONG).show()
            }
        }
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun InsertRoomData(roomDataList: List<RoomModel>) {
    val context = LocalContext.current
    val repo = remember { RoomRepository(RoomDao.getDatabase(context)) }
    val factory = remember { RoomViewModelFactory(repo) }
    val roomViewModel: RoomViewModel = viewModel(factory = factory)

    CoroutineScope(Dispatchers.IO).launch{
        roomViewModel.insert(roomDataList)
    }


}
@Composable
fun FeedScreenResponseUI(
    data: List<ArticleResponse.Legacy.Multimedia.Headline.Keywords.Person.Byline.Docs>,
    navController: NavController,

    ) {

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {


            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .weight(0.8f)
                    .background(Color.Magenta)
            ) {
                items(data) { articleItem ->
                    Log.d("TAG", "Success Result: $articleItem")
                    val articleJson = Uri.encode(Gson().toJson(articleItem))
                    Log.d("feedscreen", "FeedScreenResponseUI: $articleJson")
                    val imageUrl = articleItem.multimedia.url
                    Log.d("imageUrl", "imageUrl: $imageUrl")

                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                            .background(Color.White)
                            .clickable {
                                navController.navigate("ArticleDetailScreen/$articleJson")
                            }) {
                        Card(
                            modifier = Modifier
                                .padding(10.dp)
                                .background(Color.White)
                        ) {
                            Image(
                                painter = rememberAsyncImagePainter("https://www.nytimes.com/$imageUrl"),
                                contentDescription = "Article Image",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color.LightGray),
                                contentScale = ContentScale.Crop
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = articleItem.snippet ?: "",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Black,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(Modifier.heightIn(10.dp))
                            Text(
                                text = formatTime(articleItem.pubDate),
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Black,
                                modifier = Modifier.padding(10.dp)
                            )
                        }


                    }

                }


            }

            // Container for the button occupying 20% height

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.2f)
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Row {
                    Button(
                        onClick = {
                        },
                        modifier = Modifier
                            .padding(16.dp)
                            .height(50.dp),
                        shape = RoundedCornerShape(18.dp),


                        colors = ButtonDefaults.buttonColors(Color.Gray)
                    ) {
                        Text("Change Background", color = Color.White)
                    }
                }


            }
        }
    }
}






