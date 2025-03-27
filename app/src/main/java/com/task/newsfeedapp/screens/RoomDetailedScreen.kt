package com.task.newsfeedapp.screens

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.task.newsfeedapp.component.formatTime
import com.task.newsfeedapp.model.RoomModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoomDetailedScreen(navController: NavController, data: List<RoomModel>) {
    Log.d("roomDetail", "RoomDetailedScreen: $data")

    // Handle back press
    BackHandler {
        navController.popBackStack()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.Black
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            color = MaterialTheme.colorScheme.background
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                items(data) { it ->
                    Text(
                        text = it.newsDesk ?: "No Title",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = it.snippet ?: "No Description",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Log.d("RoomImg", "BookmarksScreen: \"https://www.nytimes.com/${it.imageUrl}\"")

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

                    Spacer(Modifier.height(10.dp))

                    Text(
                        text = formatTime(it.pubDate),
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Black
                    )

                    Spacer(Modifier.height(16.dp))
                }
            }
        }
    }
}
