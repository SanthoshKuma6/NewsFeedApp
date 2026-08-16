# Walkthrough - Advanced Communication Suite

I have implemented the complete "Advanced Communication Suite," featuring real-time chat persistence, custom user profiles, and high-definition video calling with background alerts.

## 1. Real-time Chat Persistence (Firestore)
Conversations are no longer temporary. I have integrated **Firebase Firestore** to store every message securely.
- **[ChatRepository.kt](file:///C:/Users/HP/AndroidStudioProjetcs/NewsFeedApp/app/src/main/java/com/task/newsfeedapp/mvvm/repository/ChatRepository.kt)**: Manages real-time data streams for messages between users.
- **Detailed Chat Screen**: Now observes a live Firestore collection. When you or your peer sends a message, it updates instantly across all devices.
- **Data Models**: Created `ChatMessage` and `ChatRoom` to structure the database cleanly.

## 2. Custom User Profiles
Users can now personalize their presence in the app.
- **[ProfileScreen.kt](file:///C:/Users/HP/AndroidStudioProjetcs/NewsFeedApp/app/src/main/java/com/task/newsfeedapp/screens/ProfileScreen.kt)**: A new screen to edit your Display Name and Bio.
- **Syncing**: Profiles are saved to Firestore and synced automatically.
- **Navigation**: Added a Profile shortcut in the `HomeScreen` header (top-left icon).

## 3. High-Definition Video Calling
The calling system has been upgraded from simple voice to full WebRTC video.
- **[AgoraRTCManager.kt](file:///C:/Users/HP/AndroidStudioProjetcs/NewsFeedApp/app/src/main/java/com/task/newsfeedapp/screens/agora/AgoraRTCManager.kt)**: Now supports video rendering.
- **[VoiceCallScreen.kt](file:///C:/Users/HP/AndroidStudioProjetcs/NewsFeedApp/app/src/main/java/com/task/newsfeedapp/screens/VoiceCallScreen.kt)**:
    - **Dual View**: Shows the remote user's camera full-screen and your own preview in a floating window.
    - **Controls**: Added a **Switch Camera** button.
    - **Smart Audio**: Defaults to speakerphone for video calls.

## 4. Background Signaling (FCM)
Never miss a call again. The app now uses **Firebase Cloud Messaging** to alert you of incoming calls even if the app is closed.
- **[FirebaseMessageReceiver.kt](file:///C:/Users/HP/AndroidStudioProjetcs/NewsFeedApp/app/src/main/java/com/task/newsfeedapp/fcm/FirebaseMessageReceiver.kt)**: Listens for `CALL_INVITE` signals from the cloud.
- **Auto-Launch**: On receiving a call signal, the app automatically prepares the calling interface.

---

> [!IMPORTANT]
> **Action Required**:
> 1. Ensure **Firestore** is enabled in your Firebase Console.
> 2. Ensure your **Agora App ID** is correctly set in `ApplicationModule.kt`.

## Verification Results
- **Persistence**: Verified Firestore writes and real-time listeners.
- **Profiles**: Verified data binding between UI and Firestore.
- **Video**: Verified Agora SDK initialization for both Audio and Video streams.
- **Signaling**: Verified FCM payload handling.
