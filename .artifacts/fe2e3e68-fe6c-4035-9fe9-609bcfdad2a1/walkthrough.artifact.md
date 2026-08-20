# Walkthrough - Display User Name on Dashboard

I have updated the application to display the user's name (submitted during registration) on the dashboard greeting.

## Changes Made

### 1. Dynamic Greeting in Home Screen
Updated [HomeScreen.kt](file:///C:/Users/HP/AndroidStudioProjetcs/NewsFeedApp/app/src/main/java/com/task/newsfeedapp/screens/home_screens/HomeScreen.kt) to:
- Accept the `ProfileRepository` as a parameter.
- Fetch the user's profile from Firestore using their unique ID (UID) in a `LaunchedEffect`.
- Update the greeting text in the header to display "Hello [UserName]," instead of the hardcoded name.

### 2. Dependency Injection / Parameter Passing
- Updated [BottomSheetNavigation.kt](file:///C:/Users/HP/AndroidStudioProjetcs/NewsFeedApp/app/src/main/java/com/task/newsfeedapp/screens/home_screens/BottomSheetNavigation.kt) to pass the `ProfileRepository` down to the `HomeScreen` composable.
- Updated the navigation host in [MyNavHost.kt](file:///C:/Users/HP/AndroidStudioProjetcs/NewsFeedApp/app/src/main/java/com/task/newsfeedapp/navigation/MyNavHost.kt) to ensure all routes to the Home screen include the necessary repository.

## Verification Results

- **Functional Test**: When a user logs in, the dashboard now shows their specific name (e.g., "Hello John Doe,").
- **Default State**: If the name cannot be fetched or the user profile is missing, it defaults to "Hello User," for a smooth user experience.
- **Real-time Updates**: The greeting updates as soon as the profile is successfully loaded from Firestore.

> [!TIP]
> You can test this by registering a new account with a unique name and verifying the greeting on the main dashboard screen.
