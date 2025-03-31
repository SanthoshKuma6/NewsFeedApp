package com.task.newsfeedapp.screens.home_screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.task.newsfeedapp.screens.ArticleDetailScreen
import com.task.newsfeedapp.screens.ArticleList
import com.task.newsfeedapp.screens.ArticleScreen
import com.task.newsfeedapp.screens.RoomDetailedScreen
import java.util.Locale
//
//@RequiresApi(Build.VERSION_CODES.TIRAMISU)
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun BottomSheetNavigationApp() {
//    val sheetState = rememberModalBottomSheetState()
//    val navController = rememberNavController()
//
//    val scope = rememberCoroutineScope()
//    var showSheet by remember { mutableStateOf(false) }
//
//    Scaffold(
//        bottomBar = { BottomNavigationBar(navController) }
//    ) { paddingValues ->
//        Box(modifier = Modifier.padding(paddingValues)) {
//            NavigationGraph(navController)
//        }
//    }
//
//    if (showSheet) {
//        ModalBottomSheet(
//            onDismissRequest = { showSheet = false },
//            sheetState = sheetState
//        ) {
//        }
//    }
//}
//
//@Composable
//fun BottomNavigationBar(navController: NavHostController) {
//    val items = listOf("home", "chat", "call")
//    val icons = listOf(Icons.Default.Home, Icons.Default.Email, Icons.Default.Call)
//
//    NavigationBar {
//        items.forEachIndexed { index, screen ->
//            NavigationBarItem(
//                icon = { Icon(imageVector = icons[index], contentDescription = screen) },
//                label = { Text(screen.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }) },
//                selected = navController.currentDestination?.route == screen,
//                onClick = {
//                    navController.navigate(screen) {
//                        popUpTo(navController.graph.startDestinationId) { saveState = true }
//                        launchSingleTop = true
//                        restoreState = true
//                    }
//                }
//            )
//        }
//    }
//}
//
//@RequiresApi(Build.VERSION_CODES.TIRAMISU)
//@Composable
//fun NavigationGraph(navController: NavHostController) {
//    NavHost(navController, startDestination = "home") {
//        composable("home") {
//            HomeScreen(navController)
//        }
//        composable("chat") { ChatScreen(navController) }
//        composable("call") {
//            ArticleScreen(navController)
//        }
//    }
//}
//
//
//


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetNavigationApp() {
    val sheetState = rememberModalBottomSheetState()
    var showSheet by remember { mutableStateOf(false) }
    var selectedScreen by remember { mutableStateOf("home") } // Manage screen manually

    Scaffold(
        bottomBar = { BottomNavigationBar(selectedScreen) { selectedScreen = it } }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            when (selectedScreen) {
                "home" -> HomeScreen(navController = NavHostController(LocalContext.current))
                "chat" -> ChatScreen(navController = NavHostController(LocalContext.current))
                "call" -> ArticleList(navController = NavHostController(LocalContext.current))
            }
        }
    }

    if (showSheet) {
        ModalBottomSheet(
            onDismissRequest = { showSheet = false },
            sheetState = sheetState
        ) {
            Text("This is a Bottom Sheet!")
        }
    }
}

@Composable
fun BottomNavigationBar(selectedScreen: String, onScreenSelected: (String) -> Unit) {
    val items = listOf("home", "chat", "call")
    val icons = listOf(Icons.Default.Home, Icons.Default.Email, Icons.Default.Call)

    NavigationBar {
        items.forEachIndexed { index, screen ->
            NavigationBarItem(
                icon = { Icon(imageVector = icons[index], contentDescription = screen) },
                label = { Text(screen.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }) },
                selected = selectedScreen == screen,
                onClick = { onScreenSelected(screen) }
            )
        }
    }
}



