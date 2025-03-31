package com.task.newsfeedapp.screens.home_screens


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.task.newsfeedapp.R
import com.task.newsfeedapp.screens.agora.AgoraChatManager
import com.task.newsfeedapp.screens.agora.AgoraChatScreen


@Composable
fun ChatScreen(navController: NavHostController) {
     lateinit var chatManager: AgoraChatManager

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        painter = painterResource(id = R.drawable.profile_pic), // Replace with actual image
                        contentDescription = "Profile Image",
                        modifier = Modifier
                            .size(64.dp)
                            .clip(CircleShape)
                            .background(Color.Gray),
                        contentScale = ContentScale.Crop
                    )

                    // Star Rating below Profile Picture
                    Row(
                        modifier = Modifier.padding(top = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        repeat(5) { // Display 5 stars
                            Icon(
                                imageVector = Icons.Filled.Star,
                                contentDescription = "Star Rating",
                                tint = Color(0xFFFFD700), // Gold color
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.width(8.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Lorem Ipsum",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                        Spacer(modifier = Modifier.width(4.dp))

                        Icon(
                            painterResource(R.drawable.ic_verified),
                            contentDescription = "Verified",
                            tint = Color.Green,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    Text(
                        text = "Vasthu consultation, Vedic Astrology",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )

                    Text(
                        text = "English, Tamil",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )

                    Text(
                        text = "8 Years",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "₹ 30/min",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Online",
                            fontSize = 14.sp,
                            color = Color.Green
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp)) // Adds spacing before buttons

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()), // Enable horizontal scrolling
                horizontalArrangement = Arrangement.spacedBy(10.dp) // Spacing between buttons
            ) {
                val listOfContact = listOf("Chat", "Call", "Video Call")

                listOfContact.forEach { option ->
                    when (option) {
                        "Chat" -> ActionButton(
                            iconRes = R.drawable.ic_chat,
                            label = "Chat"
                        ) {
//                            AgoraChatScreen(chatManager)
                        }
                        "Call" -> ActionButton(
                            iconRes = R.drawable.ic_call,
                            label = "Call"
                        ) {
                            // Handle button click for Call
                        }
                        "Video Call" -> ActionButton(
                            iconRes = R.drawable.ic_video,
                            label = "Video Call"
                        ) {
                            // Handle button click for Video Call
                        }
                    }
                }
            }

        }
    }
}

@Composable
fun ActionButton(iconRes: Int, label: String,onClick:()->Unit) {
    Button(
        onClick = { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Color.Gray),
        modifier = Modifier
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = label,
            modifier = Modifier.size(20.dp),
            tint = Color.Green
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = label, color = Color.Black)
    }
}





@Preview(showBackground = true)
@Composable
fun ChatPreviewProfileCard() {
    ChatScreen(navController = NavHostController(LocalContext.current))
}
