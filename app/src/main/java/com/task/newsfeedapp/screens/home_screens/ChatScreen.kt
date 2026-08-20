package com.task.newsfeedapp.screens.home_screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.filled.Send
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.task.newsfeedapp.R
import com.task.newsfeedapp.navigation.OnboardingNavigationObject
import com.task.newsfeedapp.utils.Utils

data class ChatEntry(
    val name: String,
    val username: String, // Sanitized email
    val lastMessage: String,
    val time: String,
    val imageRes: Int,
)

val dummyChats = listOf(
    ChatEntry("Sheep", "sheep_example_com", "Send me a message", "21:46", R.drawable.profile_pic),
    ChatEntry("Parrot", "parrot_example_com", "Send me a message", "21:46", R.drawable.profile_pic),
    ChatEntry("Dog", "dog_example_com", "Send me a message", "21:46", R.drawable.profile_pic),
    ChatEntry("Cat", "cat_example_com", "Send me a message", "21:46", R.drawable.profile_pic)
)

@Composable
fun ChatScreen(navController: NavHostController) {
    var newChatUsername by remember { mutableStateOf("") }
    
    // Soft gradient background (light blue to white)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF0B141B), // // Dark WhatsApp-like background
                        Color.White
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            
            Text(
                text = "Chats",
                fontSize = 28.sp,
                fontWeight = FontWeight.Black,
                color = Color.White,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Start New Chat Section
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = newChatUsername,
                    onValueChange = { newChatUsername = it },
                    placeholder = { Text("Enter email to chat", color = Color.Gray) },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.Gray
                    ),
                    singleLine = true
                )
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(
                    onClick = {
                        if (newChatUsername.isNotBlank()) {
                            val sanitized = Utils.sanitizeEmail(newChatUsername)
                            navController.navigate("${OnboardingNavigationObject.DETAILED_CHAT_SCREEN}/$sanitized")
                            newChatUsername = ""
                        }
                    },
                    modifier = Modifier
                        .size(56.dp)
                        .background(Color(0xFF00A884), CircleShape)
                ) {
                    Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "Start Chat", tint = Color.White)
                }
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(dummyChats) { chat ->
                    ChatListItem(chat) {
                        navController.navigate("${OnboardingNavigationObject.DETAILED_CHAT_SCREEN}/${chat.username}")
                    }
                }
            }
        }
    }
}

@Composable
fun ChatListItem(chat: ChatEntry, onClick: () -> Unit) {
    Column(
        modifier = Modifier.clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = chat.imageRes),
                contentDescription = "Profile Image",
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = chat.name,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = chat.lastMessage,
                    fontSize = 14.sp,
                    color = Color.White
                )
            }

            Text(
                text = chat.time,
                fontSize = 14.sp,
                color = Color.White,
                modifier = Modifier.align(Alignment.Top)
            )
        }
        // Optional: Add a very light divider if needed, but the image shows a clean list
        // HorizontalDivider(color = Color.LightGray.copy(alpha = 0.2f), thickness = 0.5.dp)
    }
}

@Preview(showBackground = true)
@Composable
fun ChatPreview() {
    // ChatScreen(navController = NavHostController(LocalContext.current))
}
