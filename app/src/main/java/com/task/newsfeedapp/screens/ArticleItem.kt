package com.task.newsfeedapp.screens

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.google.gson.Gson
import com.task.newsfeedapp.R
import com.task.newsfeedapp.component.CircularLoader
import com.task.newsfeedapp.component.LinearLoader
import com.task.newsfeedapp.component.formatTime
import com.task.newsfeedapp.dao.RoomDao
import com.task.newsfeedapp.factory.RoomViewModelFactory
import com.task.newsfeedapp.factory.ViewModelFactory
import com.task.newsfeedapp.model.ArticleResponse
import com.task.newsfeedapp.mvvm.repository.ArticleRepo
import com.task.newsfeedapp.mvvm.viewmodel.ArticleViewModel
import com.task.newsfeedapp.mvvm.repository.RoomRepository
import com.task.newsfeedapp.mvvm.viewmodel.RoomViewModel
import com.task.newsfeedapp.network.ApiService
import com.task.newsfeedapp.resource.RoomResource
import com.task.newsfeedapp.utils.Utils

/**
 * SANTHOSH
 */
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ArticleList(article: List<ArticleResponse>, navController: NavController) {
    val searchQuery = remember { mutableStateOf("") }
    val filteredArticles = article.filter {
        article.toString().contains(searchQuery.value, ignoreCase = true)

    }

    Scaffold(topBar = {
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
            NewsFeedScreen(article = filteredArticles, navController)
        }
    }, content = {
        Log.d("TAG", "ArticleList: $article")
    })
}

@Composable
fun NewsFeedScreen(article: List<ArticleResponse>, navController: NavController) {
    val tabIndex = remember { mutableStateOf(0) }
    Column(modifier = Modifier.fillMaxSize()) {
        TabLayout(tabIndex)
        TabContent(tabIndex.value, article, navController)
    }
}

@Composable
fun TabLayout(tabIndex: MutableState<Int>) {
    val tabs = listOf("Feeds", "Bookmarks")
    TabRow(modifier = Modifier.fillMaxWidth(),
        selectedTabIndex = tabIndex.value,
        containerColor = Color.White,
        indicator = { tabPositions ->
            SecondaryIndicator(
                modifier = Modifier.tabIndicatorOffset(tabPositions[tabIndex.value]),
                color = Color.Black
            )
        }) {
        tabs.forEachIndexed { index, title ->
            Tab(text = {
                Text(
                    text = title, style = TextStyle(
                        fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.Black
                    )
                )
            }, selected = tabIndex.value == index, onClick = { tabIndex.value = index })
        }
    }
}

@Composable
fun TabContent(tabIndex: Int, article: List<ArticleResponse>, navController: NavController) {
    when (tabIndex) {
        0 -> FeedsScreen(navController)
        1 -> BookmarksScreen()
    }
}

@Composable
fun FeedsScreen(navController: NavController) {
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }
    var backgroundImageUri by remember { mutableStateOf<Uri?>(null) }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            backgroundImageUri = it
            Log.d("TAG", "backgroundImageUri: $it")
        }
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap: Bitmap? ->
        bitmap?.let {
            val uri = saveImageToGallery(context, it)
            backgroundImageUri = uri
        }
    }

    val viewModel: ArticleViewModel = viewModel(
        factory = ViewModelFactory(
            ArticleRepo(ApiService.NetworkClient.apiService), context
        )
    )
    val articleItems = viewModel.articlePager.collectAsLazyPagingItems()

    Box(modifier = Modifier) {


        Column(modifier = Modifier.fillMaxSize()) {
            backgroundImageUri?.let {
                Image(
                    painter = rememberAsyncImagePainter(it, contentScale = ContentScale.Fit),
                    contentDescription = "Backgrounf image",
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.onBackground)
                )

            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.8f)
                    .background(Color.White)
            ) {
                items(articleItems.itemCount) { articleItem ->
                    Log.d("TAG", "FeedsScreen: $articleItem")
                    val docs = articleItems[articleItem]
                    val articleJson = Uri.encode(Gson().toJson(docs))
                    val imageUrl = docs?.multimedia?.firstOrNull()?.url.let { it }
                    Log.d("imageUrl", "imageUrl: $imageUrl")

                    Column(modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .clickable {
                            navController.navigate("ArticleDetailScreen/$articleJson")
                        }) {
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
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(imageUrl)
                                .crossfade(true)
                                .error(R.drawable.ic_launcher_foreground) // Fallback image in case of an error
                                .build(),
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
                    when {
                        loadState.refresh is LoadState.Loading -> {
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

            // Container for the button occupying 20% height

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.2f)
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    onClick = {
                        showDialog = true
                    },
                    modifier = Modifier
                        .padding(16.dp)
                        .height(50.dp),
                    shape = RoundedCornerShape(18.dp),


                    colors = ButtonDefaults.buttonColors(Color.Gray)
                ) {
                    Text("Change Background", color = Color.White)
                }
                if (showDialog) {
                    ShowImagePickerDialog(onDismiss = { showDialog = false },
                        onPickGallery = { galleryLauncher.launch("image/*") },
                        onCapturePhoto = { cameraLauncher.launch(null) })
                }
            }
        }
    }
}

@Composable
fun ShowImagePickerDialog(
    onDismiss: () -> Unit, onPickGallery: () -> Unit, onCapturePhoto: () -> Unit
) {
    AlertDialog(onDismissRequest = { onDismiss() },
        title = { Text("Select Image Source") },
        text = {
            Column {
                Button(onClick = {
                    onPickGallery()
                    onDismiss()
                }) {
                    Text("Choose from Gallery")
                }

                Spacer(modifier = Modifier.height(8.dp))

                Button(onClick = {
                    onCapturePhoto()
                    onDismiss()
                }) {
                    Text("Take a Photo")
                }
            }
        },
        confirmButton = {},
        dismissButton = {
            Button(onClick = { onDismiss() }) {
                Text("Cancel")
            }
        })
}

fun saveImageToGallery(context: Context, bitmap: Bitmap): Uri? {
    val contentValues = ContentValues().apply {
        put(
            MediaStore.Images.Media.DISPLAY_NAME, "Captured_Image_${System.currentTimeMillis()}.jpg"
        )
        put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/CameraApp")
        put(MediaStore.Images.Media.IS_PENDING, 1)
    }

    val contentResolver = context.contentResolver
    val uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

    uri?.let { outputStream ->
        contentResolver.openOutputStream(outputStream)?.use { outStream ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream)
        }
        contentValues.clear()
        contentValues.put(MediaStore.Images.Media.IS_PENDING, 0)
        contentResolver.update(uri, contentValues, null, null)
    }

    return uri
}


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun BookmarksScreen() {
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


