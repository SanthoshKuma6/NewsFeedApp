package com.task.newsfeedapp.model

import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

data class ChatMessage(
    val id: String = "",
    val senderId: String = "",
    val receiverId: String = "",
    val text: String = "",
    @ServerTimestamp val timestamp: Date? = null,
    val isRead: Boolean = false
)

data class ChatRoom(
    val id: String = "",
    val participantIds: List<String> = emptyList(),
    val lastMessage: String = "",
    @ServerTimestamp val lastUpdated: Date? = null
)
