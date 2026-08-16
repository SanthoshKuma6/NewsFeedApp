# Implementation Plan - Detailed Chat Screen UI

This plan implements a new "Detailed Chat Screen" that matches the provided UI, allowing users to see a conversation and send messages.

## User Review Required

> [!IMPORTANT]
> The new screen will feature a toolbar with the user's name and avatar, a message list with distinct styles for sent and received messages, and a bottom input bar.

## Proposed Changes

### UI Layer

#### [NEW] [DetailedChatScreen.kt](file:///C:/Users/HP/AndroidStudioProjetcs/NewsFeedApp/app/src/main/java/com/task/newsfeedapp/screens/DetailedChatScreen.kt)

- **Toolbar**:
    - Back navigation button.
    - Circular avatar of the person being chatted with.
    - Name of the person (bold).
- **Message List**:
    - `LazyColumn` for messages.
    - `ReceivedMessageItem`: Avatar on the left, light blue bubble.
    - `SentMessageItem`: Dark blue bubble on the right.
- **Input Bar**:
    - Camera and Image icons.
    - Rounded text field with "Message" hint.
    - Circular send button with a send icon.

### Navigation Layer

#### [MODIFY] [MyNavHost.kt](file:///C:/Users/HP/AndroidStudioProjetcs/NewsFeedApp/app/src/main/java/com/task/newsfeedapp/navigation/MyNavHost.kt)

- Add a new composable route: `"DetailedChatScreen/{userName}"`.
- Pass the `userName` to `DetailedChatScreen`.

#### [MODIFY] [ChatScreen.kt](file:///C:/Users/HP/AndroidStudioProjetcs/NewsFeedApp/app/src/main/java/com/task/newsfeedapp/screens/home_screens/ChatScreen.kt)

- Add a `onChatClick` lambda to `ChatListItem`.
- Update `ChatScreen` to navigate to `"DetailedChatScreen/${chat.name}"` when a list item is clicked.

## Verification Plan

### Manual Verification
1. Open the app and go to the "Chat" menu.
2. Click on a chat entry (e.g., "Parrot").
3. Verify the `DetailedChatScreen` opens with the correct name and avatar in the toolbar.
4. Verify the message bubbles appear as shown in the reference image.
5. Verify the bottom input bar is visible and matches the design.
6. Click the back button and ensure it returns to the chat list.
