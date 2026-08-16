package com.task.newsfeedapp.mvvm.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.task.newsfeedapp.model.ChatMessage
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import javax.inject.Singleton

import kotlinx.coroutines.tasks.await

@Singleton
class ChatRepository @Inject constructor() {
    private val firestore = FirebaseFirestore.getInstance()

    fun getMessages(senderId: String, receiverId: String): Flow<List<ChatMessage>> = callbackFlow {
        val chatId = getChatId(senderId, receiverId)
        val subscription = firestore.collection("chats")
            .document(chatId)
            .collection("messages")
            .orderBy("timestamp", Query.Direction.ASCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    Log.e("ChatRepository", "Error fetching messages for chatId: $chatId", error)
                    close(error)
                    return@addSnapshotListener
                }
                if (snapshot != null) {
                    val messages = snapshot.toObjects(ChatMessage::class.java)
                    trySend(messages)
                }
            }
        awaitClose { subscription.remove() }
    }

    suspend fun sendMessage(message: ChatMessage) {
        val chatId = getChatId(message.senderId, message.receiverId)
        try {
            val docRef = firestore.collection("chats")
                .document(chatId)
                .collection("messages")
                .document()
            
            val messageWithId = message.copy(id = docRef.id)
            docRef.set(messageWithId).await()
            
            // Update last message in room
            firestore.collection("chats").document(chatId).set(
                mapOf(
                    "lastMessage" to message.text,
                    "lastUpdated" to com.google.firebase.firestore.FieldValue.serverTimestamp(),
                    "participantIds" to listOf(message.senderId, message.receiverId)
                )
            ).await()
        } catch (e: Exception) {
            Log.e("ChatRepository", "Error sending message in chatId: $chatId", e)
        }
    }

    private fun getChatId(id1: String, id2: String): String {
        return if (id1 < id2) "${id1}_$id2" else "${id2}_$id1"
    }
}
