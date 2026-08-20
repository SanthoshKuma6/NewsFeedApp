# Implementation Plan - Display User Name on Dashboard

The goal is to display the user's name (submitted during registration) on the dashboard after the "Hello" greeting.

## Proposed Changes

### 1. Update Home Screen to Fetch Profile
#### [MODIFY] [HomeScreen.kt](file:///C:/Users/HP/AndroidStudioProjetcs/NewsFeedApp/app/src/main/java/com/task/newsfeedapp/screens/home_screens/HomeScreen.kt)
*   Add `profileRepository: ProfileRepository` to the `HomeScreen` composable parameters.
*   Use `LaunchedEffect` to fetch the user profile from Firestore using the current user's UID.
*   Maintain a state for the user's display name (defaulting to "User" or similar).
*   Pass the fetched name to `HeaderSection`.
*   Update `HeaderSection` to accept the `userName` and display it in the greeting text (e.g., "Hello $userName,").

### 2. Update Bottom Sheet Navigation
#### [MODIFY] [BottomSheetNavigation.kt](file:///C:/Users/HP/AndroidStudioProjetcs/NewsFeedApp/app/src/main/java/com/task/newsfeedapp/screens/home_screens/BottomSheetNavigation.kt)
*   Pass the `profileRepository` (which is already a parameter of `BottomSheetNavigationApp`) to the `HomeScreen` call.

### 3. Update Nav Host
#### [MODIFY] [MyNavHost.kt](file:///C:/Users/HP/AndroidStudioProjetcs/NewsFeedApp/app/src/main/java/com/task/newsfeedapp/navigation/MyNavHost.kt)
*   Update the `composable("HomeScreen")` route to pass the `profileRepository` to `HomeScreen`.

## Verification Plan

### Manual Verification
1.  **Register**: Create a new account with a specific name (e.g., "John Doe").
2.  **Dashboard Check**: After registration and navigation to the dashboard, verify that the header displays "Hello John Doe,".
3.  **Login Check**: Log out and log back in with the same account. Verify the name still appears correctly.
