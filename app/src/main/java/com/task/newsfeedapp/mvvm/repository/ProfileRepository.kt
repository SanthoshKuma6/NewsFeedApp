package com.task.newsfeedapp.mvvm.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.task.newsfeedapp.model.UserProfile
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileRepository @Inject constructor() {
    private val firestore = FirebaseFirestore.getInstance()

    suspend fun getUserProfile(uid: String): UserProfile? {
        return try {
            firestore.collection("users").document(uid).get().await().toObject(UserProfile::class.java)
        } catch (e: Exception) {
            Log.e("ProfileRepository", "Error getting user profile for uid: $uid", e)
            null
        }
    }

    suspend fun saveUserProfile(profile: UserProfile) {
        try {
            firestore.collection("users").document(profile.uid).set(profile).await()
        } catch (e: Exception) {
            Log.e("ProfileRepository", "Error saving user profile for uid: ${profile.uid}", e)
        }
    }
}
