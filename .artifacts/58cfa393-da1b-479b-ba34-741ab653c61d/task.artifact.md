# Task List - Advanced Communication Suite

## 1. Chat Persistence (Firestore)
- `[ ]` Create `ChatMessage` and `ChatRoom` data models for Firestore
- `[ ]` Implement `ChatRepository.kt` for Firestore operations
- `[ ]` Update `DetailedChatScreen.kt` to observe Firestore real-time updates
- `[ ]` Replace local state in `DetailedChatScreen` with repository-backed state

## 2. User Profiles
- `[ ]` Create `UserProfile` data model
- `[ ]` Implement `ProfileRepository.kt` for profile sync
- `[ ]` Create `ProfileScreen.kt` UI
- `[ ]` Add Profile navigation from `HomeScreen` header

## 3. Video Calling & Background Signaling
- `[ ]` Update `AgoraRTCManager.kt` to support Video Canvas
- `[ ]` Update `VoiceCallScreen.kt` (redesign to `CallScreen`) to show Video feeds
- `[ ]` Implement FCM signaling in `FirebaseMessageReceiver.kt` for background alerts
- `[ ]` Update `AgoraChatManager` to send signaling via FCM when RTM is disconnected

## 4. Verification
- `[ ]` Verify Firestore message persistence
- `[ ]` Verify Profile updates reflect in chat
- `[ ]` Verify Video call connectivity
- `[ ]` Verify Background call notifications
