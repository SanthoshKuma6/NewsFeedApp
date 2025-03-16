package com.task.newsfeedapp.screens

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.task.newsfeedapp.component.calculateReadTimeWithDateCheck
import com.task.newsfeedapp.component.formatTime
import com.task.newsfeedapp.model.ArticleResponse


/**
 * SANTHOSH
 */

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("ContextCastToActivity")
@Composable
fun ArticleDetailScreen(
    navController: NavController,
    article: ArticleResponse.Legacy.Multimedia.Headline.Keywords.Person.Byline.Docs?
) {
    val context = LocalContext.current as? Activity
    var auth = ""
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp, start = 16.dp, end = 16.dp) // Avoids status bar overlap
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.navigateUp() }) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
            }

            Row {
                IconButton(onClick = { }) {
                    Icon(Icons.Filled.Share, contentDescription = "Share")
                }
                IconButton(onClick = { /* TODO: Add bookmark functionality */ }) { // Bookmark Button
                    Icon(Icons.Filled.Star, contentDescription = "Bookmark")
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        article?.let {

            Spacer(modifier = Modifier.height(8.dp))
            Image(
                painter = rememberAsyncImagePainter(it.uri),
                contentDescription = "Article Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.LightGray),
                contentScale = ContentScale.Crop
            )

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
//                Text(
//                    text = formatTime(it.pubDate.toString()),
//                    style = MaterialTheme.typography.bodySmall
//                )
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
        } ?: Text(text = "No article data available")
    }
}
