# Implementation Plan - Advanced Communication Suite

This plan implements three major feature sets: Real-time Chat Persistence, User Profiles, and Video Calling with Background Notifications.

## User Review Required

> [!WARNING]
> - **Firebase Setup**: These features require **Firestore** and **Cloud Storage** to be enabled in your Firebase Console.
> - **Agora ID**: Ensure your Agora App ID is valid, as video calling consumes more resources.

## Proposed Changes

### 1. Real-time Chat Persistence (Firestore)

#### [NEW] [ChatRepository.kt](file:///C:/Users/HP/AndroidStudioProjetcs/NewsFeedApp/app/src/main/java/com/task/newsfeedapp/mvvm/repository/ChatRepository.kt)
- Methods to `sendMessage` and `getMessages(peerId)` using Firestore collections (`chats/{chatId}/messages`).

#### [MODIFY] [DetailedChatScreen.kt](file:///C:/Users/HP/AndroidStudioProjetcs/NewsFeedApp/app/src/main/java/com/task/newsfeedapp/screens/DetailedChatScreen.kt)
- Replace dummy list with a real-time stream from Firestore.
- Messages will now persist even after closing the app.

---

### 2. User Profiles & Avatars

#### [MODIFY] [HomeScreen.kt](file:///C:/Users/HP/AndroidStudioProjetcs/NewsFeedApp/app/src/main/java/com/task/newsfeedapp/screens/home_screens/HomeScreen.kt)
- Add a "Settings" or "Profile" icon to the header.

#### [NEW] [ProfileScreen.kt](file:///C:/Users/HP/AndroidStudioProjetcs/NewsFeedApp/app/src/main/java/com/task/newsfeedapp/screens/ProfileScreen.kt)
- UI to display and edit display name and upload a profile picture.
- Sync profile data with Firebase Auth and Firestore.

---

### 3. Video Calling & Background Notifications

#### [MODIFY] [AgoraRTCManager.kt](file:///C:/Users/HP/AndroidStudioProjetcs/NewsFeedApp/app/src/main/java/com/task/newsfeedapp/screens/agora/AgoraRTCManager.kt)
- Enable video stream support.
- Add methods to manage local and remote video surfaces.

#### [MODIFY] [VoiceCallScreen.kt](file:///C:/Users/HP/AndroidStudioProjetcs/NewsFeedApp/app/src/main/java/com/task/newsfeedapp/screens/VoiceCallScreen.kt)
- Add a "Switch to Video" button.
- Implement camera preview using `AndroidView` for Agora's `VideoCanvas`.

#### [MODIFY] [FirebaseMessageReceiver.kt](file:///C:/Users/HP/AndroidStudioProjetcs/NewsFeedApp/app/src/main/java/com/task/newsfeedapp/fcm/FirebaseMessageReceiver.kt)
- Handle "CALL_INVITE" data messages from FCM.
- Show a full-screen notification or high-priority alert to answer calls when the app is in the background.

## Verification Plan

### Manual Verification
1. **Persistence**: Send a message, kill the app, reopen, and verify the message is still there.
2. **Profiles**: Change your name in settings and verify it updates in the Chat list for other users.
3. **Video**: Start a call and verify both users can see each other's camera feed.
4. **Background**: Lock your phone, have another user call you, and verify you receive a call notification.
