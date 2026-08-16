package com.task.newsfeedapp.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.gson.Gson
import com.task.newsfeedapp.base.BaseViewModel
import com.task.newsfeedapp.base.SplashViewModel
import com.task.newsfeedapp.model.ArticleResponse
import com.task.newsfeedapp.model.RoomModel
import com.task.newsfeedapp.mvvm.viewmodel.AuthViewModel
import com.task.newsfeedapp.utils.state.AuthState
import com.task.newsfeedapp.screens.ArticleDetailScreen
import com.task.newsfeedapp.screens.DetailedChatScreen
import com.task.newsfeedapp.screens.agora.AgoraChatManager
import com.task.newsfeedapp.screens.ArticleScreen
import com.task.newsfeedapp.screens.RoomDetailedScreen
import com.task.newsfeedapp.screens.agora.AgoraChatScreen
import com.task.newsfeedapp.screens.home_screens.BottomSheetNavigationApp
import com.task.newsfeedapp.screens.home_screens.ChatScreen
import com.task.newsfeedapp.screens.home_screens.HomeScreen
import com.task.newsfeedapp.screens.login_screesn.LoginScreen
import com.task.newsfeedapp.screens.login_screesn.OtpVerificationScreen
import com.task.newsfeedapp.screens.login_screesn.SignUpScreen
import com.task.newsfeedapp.screens.login_screesn.VerificationSuccess

object OnboardingNavigationObject {
    const val LOGIN_SCREEN = "LoginScreen"
    const val SIGNUP_SCREEN = "SignUpScreen"
    const val OTP_SCREEN = "OtpVerificationScreen"
    const val OTP_SUCCESS_SCREEN = "VerificationSuccess"
    const val BOTTOM_SHEET_SCREEN = "BottomSheetNavigationApp"
    const val AGORA_CHAT_SCREEN="AgoraChatScreen"
    const val SPLASH_SCREEN = "SplashScreen"
    const val DETAILED_CHAT_SCREEN = "DetailedChatScreen"

}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun MyNavHost(authViewModel: BaseViewModel, chatManager: AgoraChatManager) {
    val splashViewModel = authViewModel as? SplashViewModel
            ?: throw IllegalStateException("Invalid ViewModel Type")

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = OnboardingNavigationObject.SPLASH_SCREEN) {
        composable(OnboardingNavigationObject.SPLASH_SCREEN) {
            SplashScreen(navController, splashViewModel)
        }
        composable(OnboardingNavigationObject.LOGIN_SCREEN) {
            LoginScreen(navController, splashViewModel)
        }
        composable(OnboardingNavigationObject.SIGNUP_SCREEN) {
            SignUpScreen(navController,splashViewModel)

        }
        composable(OnboardingNavigationObject.OTP_SCREEN) {
            OtpVerificationScreen(navController)
        }
        composable(OnboardingNavigationObject.OTP_SUCCESS_SCREEN) {
            VerificationSuccess(navController)
        }
        composable(OnboardingNavigationObject.BOTTOM_SHEET_SCREEN) {
            BottomSheetNavigationApp(navController,splashViewModel, chatManager)
        }
        composable("ArticleScreen") {
            ArticleScreen(navController)
        }
        composable("HomeScreen") {
            HomeScreen(navController,splashViewModel)
        }
        composable("ChatScreen") {
            ChatScreen(navController)
        }
        composable(OnboardingNavigationObject.AGORA_CHAT_SCREEN) {
            AgoraChatScreen(navController)
        }
        composable("${OnboardingNavigationObject.DETAILED_CHAT_SCREEN}/{userName}") { backStackEntry ->
            val userName = backStackEntry.arguments?.getString("userName") ?: ""
            DetailedChatScreen(navController, userName, chatManager)
        }
        composable(
            "ArticleDetailScreen/{articleJson}",
            arguments = listOf(navArgument("articleJson") { type = NavType.StringType })
        ) { backStackEntry ->
            val json = backStackEntry.arguments?.getString("articleJson")
            val article = json?.let {
                Gson().fromJson(
                    it,
                    ArticleResponse.Legacy.Multimedia.Headline.Keywords.Person.Byline.Docs::class.java
                )
            }
            ArticleDetailScreen(navController, listOf(article))
        }

        composable("RoomDetailedScreen/{roomJson}", arguments = listOf(navArgument("roomJson") {
            type = NavType.StringType
        })) { backStackEntry ->
            val json = backStackEntry.arguments?.getString("roomJson")
            val roomArticle = json!!.let { Gson().fromJson(it, RoomModel::class.java) }
            RoomDetailedScreen( navController,listOf(roomArticle))
        }


    }
}

@Composable
fun SplashScreen(navController: NavHostController, viewModel: BaseViewModel) {
    val authState by viewModel.authState.observeAsState()

    LaunchedEffect(authState) {
        when (authState) {
            is AuthState.Authenticate -> {
                navController.navigate(OnboardingNavigationObject.BOTTOM_SHEET_SCREEN) {
                    popUpTo(OnboardingNavigationObject.SPLASH_SCREEN) { inclusive = true }
                }
            }
            is AuthState.UnAuthenticated -> {
                navController.navigate(OnboardingNavigationObject.LOGIN_SCREEN) {
                    popUpTo(OnboardingNavigationObject.SPLASH_SCREEN) { inclusive = true }
                }
            }
            else -> Unit
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}