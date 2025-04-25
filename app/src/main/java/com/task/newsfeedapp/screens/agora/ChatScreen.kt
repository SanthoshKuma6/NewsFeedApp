package com.task.newsfeedapp.screens.agora

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun AgoraChatScreen(navController: NavHostController) {
    val messages = remember { mutableStateListOf<String>() }
    var message by remember { mutableStateOf("") }
    var chatManager = AgoraChatManager(LocalContext.current,"")

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(messages) { msg ->
                Text(msg, modifier = Modifier.padding(8.dp))
            }
        }
        Row {
            TextField(value = message, onValueChange = { message = it })
            Button(onClick = {
                chatManager.sendMessage(message) { success ->
                    if (success) {
                        messages.add("Me: $message")
                        message = ""
                    }
                }
            }) {
                Text("Send")
            }
        }
    }
}
