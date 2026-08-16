package com.task.newsfeedapp.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.task.newsfeedapp.R
import com.task.newsfeedapp.model.ChatMessage as FirestoreChatMessage
import com.task.newsfeedapp.mvvm.repository.ChatRepository
import com.task.newsfeedapp.screens.agora.AgoraChatManager
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

data class ChatMessage(
    val text: String,
    val isFromMe: Boolean,
    val time: String,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailedChatScreen(
    navController: NavController,
    userName: String,
    chatManager: AgoraChatManager,
    chatRepository: ChatRepository
) {
    val currentUserId = com.google.firebase.auth.FirebaseAuth.getInstance().currentUser?.uid ?: "unknown"
    
    // Safely handle the messages flow to prevent crashes on PERMISSION_DENIED
    val messagesFlow = remember(currentUserId, userName) {
        chatRepository.getMessages(currentUserId, userName)
            .catch { e ->
                Log.e("DetailedChatScreen", "Permission denied or error fetching messages for $userName", e)
                emit(emptyList())
            }
    }
    val messagesState = messagesFlow.collectAsState(initial = emptyList())
    
    val uiMessages = messagesState.value.map { 
        ChatMessage(it.text, it.senderId == currentUserId, "21:46") 
    }

    LaunchedEffect(currentUserId) {
        if (currentUserId == "unknown") {
            Log.w("DetailedChatScreen", "User is not authenticated. Popping backstack.")
            navController.popBackStack()
        }
    }

    LaunchedEffect(Unit) {
        chatManager.onMessageReceived = { senderId, text ->
            // Already handled by Firestore real-time updates if both use Firestore
            // But we keep RTM for immediate feedback or if Firestore is slow
        }
    }

    var inputText by remember { mutableStateOf("") }

    val coroutineScope = rememberCoroutineScope()

    DetailedChatContent(
        userName = userName,
        messages = uiMessages,
        inputText = inputText,
        onBackClick = { navController.popBackStack() },
        onTextChange = { inputText = it },
        onSend = {
            if (inputText.isNotBlank()) {
                val messageToSend = inputText
                coroutineScope.launch {
                    chatRepository.sendMessage(
                        FirestoreChatMessage(
                            senderId = currentUserId,
                            receiverId = userName,
                            text = messageToSend
                        )
                    )
                }
                // Optional: Send RTM signal for typing or immediate delivery
                chatManager.sendPeerMessage(userName, messageToSend) { }
                inputText = ""
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailedChatContent(
    userName: String,
    messages: List<ChatMessage>,
    inputText: String,
    onBackClick: () -> Unit,
    onTextChange: (String) -> Unit,
    onSend: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.profile_pic),
                            contentDescription = null,
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = userName,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Black
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        bottomBar = {
            ChatInputBar(
                text = inputText,
                onTextChange = onTextChange,
                onSend = onSend
            )
        },
        containerColor = Color(0xFFF8F9FB) // Light grey/blue background
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            reverseLayout = false
        ) {
            item { Spacer(modifier = Modifier.height(16.dp)) }
            items(messages) { msg ->
                MessageBubble(msg)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun MessageBubble(message: ChatMessage) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = if (message.isFromMe) Alignment.CenterEnd else Alignment.CenterStart
    ) {
        Row(verticalAlignment = Alignment.Bottom) {
            if (!message.isFromMe) {
                Image(
                    painter = painterResource(id = R.drawable.profile_pic),
                    contentDescription = null,
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
            
            Surface(
                color = if (message.isFromMe) Color(0xFF3F5C7A) else Color(0xFFE3EBF5),
                shape = RoundedCornerShape(
                    topStart = 16.dp,
                    topEnd = 16.dp,
                    bottomStart = if (message.isFromMe) 16.dp else 4.dp,
                    bottomEnd = if (message.isFromMe) 4.dp else 16.dp
                )
            ) {
                Text(
                    text = message.text,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
                    color = if (message.isFromMe) Color.White else Color.Black,
                    fontSize = 15.sp
                )
            }
        }
    }
}

@Composable
fun ChatInputBar(
    text: String,
    onTextChange: (String) -> Unit,
    onSend: () -> Unit
) {
    Surface(
        color = Color.White,
        modifier = Modifier.fillMaxWidth(),
        shadowElevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .navigationBarsPadding()
                .imePadding(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { /* Camera */ }) {
                Icon(Icons.Default.Add, contentDescription = "Camera", tint = Color(0xFF3F5C7A))
            }
            IconButton(onClick = { /* Gallery */ }) {
                Icon(Icons.Default.Share, contentDescription = "Gallery", tint = Color(0xFF3F5C7A))
            }
            
            TextField(
                value = text,
                onValueChange = onTextChange,
                modifier = Modifier
                    .weight(1f)
                    .heightIn(min = 40.dp),
                placeholder = { Text("Message", color = Color.Gray) },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFF1F3F6),
                    unfocusedContainerColor = Color(0xFFF1F3F6),
                    disabledContainerColor = Color(0xFFF1F3F6),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                shape = RoundedCornerShape(24.dp)
            )
            
            Spacer(modifier = Modifier.width(8.dp))
            
            FloatingActionButton(
                onClick = onSend,
                modifier = Modifier.size(48.dp),
                containerColor = if (text.isNotBlank()) Color(0xFF3F5C7A) else Color(0xFFE0E0E0),
                contentColor = Color.White,
                shape = CircleShape,
                elevation = FloatingActionButtonDefaults.elevation(0.dp, 0.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Send,
                    contentDescription = "Send",
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailedChatPreview() {
    DetailedChatContent(
        userName = "Parrot",
        messages = listOf(
            ChatMessage("I will reply in 5 seconds", false, "21:46"),
            ChatMessage("Send me a message", false, "21:46"),
            ChatMessage("Well hello there!", true, "21:46"),
            ChatMessage("Well hello there!", false, "21:46")
        ),
        inputText = "",
        onBackClick = {},
        onTextChange = {},
        onSend = {}
    )
}
