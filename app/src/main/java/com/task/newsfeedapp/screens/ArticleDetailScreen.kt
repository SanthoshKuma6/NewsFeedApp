package com.task.newsfeedapp.screens

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import com.task.newsfeedapp.component.AnimatedLoader
import com.task.newsfeedapp.component.calculateReadTimeWithDateCheck
import com.task.newsfeedapp.model.ArticleResponse


/**
 * SANTHOSH
 */

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("ContextCastToActivity", "UnusedMaterial3ScaffoldPaddingParameter",
    "SuspiciousIndentation"
)
@Composable
fun ArticleDetailScreen(
    navController: NavController,
    article: List<ArticleResponse.Legacy.Multimedia.Headline.Keywords.Person.Byline.Docs?>
) {
    var isFavorite by remember { mutableStateOf(false) } // Favorite toggle state
    val context = LocalContext.current

    var auth = ""
    Scaffold(topBar ={
        TopAppBar(
            title = { Text("Back") },
            navigationIcon = {
                IconButton(onClick = {navController.navigateUp()}) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.Black
                    )
                }

            } ,
            actions = {
                // Share Button
                IconButton(onClick = {
                    val shareIntent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, "Check this out: ${article.firstOrNull()?.snippet}")
                        type = "text/plain"
                    }
                    context.startActivity(Intent.createChooser(shareIntent, "Share via"))

                }) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "Share",
                        tint = Color.Black
                    )
                }

                // Favorite Button (Toggle)
                IconButton(onClick = {
                    isFavorite = !isFavorite
                }) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Favorite",
                        tint = if (isFavorite) Color.Red else Color.Black
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
        )
    }
    ) { innerPadding ->
            Surface(modifier = Modifier.fillMaxSize().padding(innerPadding), color = MaterialTheme.colorScheme.background) {

                LazyColumn {
                    items(article) {
                        it!!
                        Spacer(modifier = Modifier.height(8.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color.LightGray),
                            contentAlignment = Alignment.Center
                        ) {
                            SubcomposeAsyncImage(
                                model = "https://www.nytimes.com/${it.multimedia.firstOrNull()!!.url}",
                                contentDescription = "Article Image",
                                loading = {
                                    AnimatedLoader()
                                },
                                error = {
                                    Text(text = "Failed to load image", color = Color.Red)
                                },
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = it.snippet ?: "No Snippet Available",
                            style = MaterialTheme.typography.bodySmall,

                            )
                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = it.headline?.main ?: "No abstract Available",
                            style = MaterialTheme.typography.headlineSmall,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold

                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = it.abstract ?: "No abstract Available",
                            style = MaterialTheme.typography.headlineSmall
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        for (i in it.byline!!.person) {
                            auth = i.firstname.toString()
                        }
                        Text(
                            text = "by $auth" ?: "No bye line",
                            style = MaterialTheme.typography.bodySmall
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Row {
                            Spacer(Modifier.heightIn(25.dp))
                            Text(
                                text = calculateReadTimeWithDateCheck(it.pubDate.toString()),
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = it.leadParagraph ?: "No leadParagraph Available",
                            style = MaterialTheme.typography.labelLarge
                        )

                    }

                }

            }

    }




}
