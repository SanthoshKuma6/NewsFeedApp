package com.task.newsfeedapp.model

data class UserProfile(
    val uid: String = "",
    val displayName: String = "",
    val email: String = "",
    val photoUrl: String = "",
    val bio: String = "",
    val fcmToken: String = "",
    val username: String = "",
    val fullName: String = "",
    val country: String = "",
    val phoneNumber: String = "",
    val gender: String = "", // "Male" or "Female"
    val dateOfBirth: String = "" // "MM/DD/YYYY"
)
