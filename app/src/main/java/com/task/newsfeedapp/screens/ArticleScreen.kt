package com.task.newsfeedapp.screens

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.task.newsfeedapp.dao.RoomDao
import com.task.newsfeedapp.factory.RoomViewModelFactory
import com.task.newsfeedapp.factory.ViewModelFactory
import com.task.newsfeedapp.model.fcm.getDeviceToken
import com.task.newsfeedapp.mvvm.repository.ArticleRepo
import com.task.newsfeedapp.mvvm.repository.RoomRepository
import com.task.newsfeedapp.mvvm.viewmodel.ArticleViewModel
import com.task.newsfeedapp.mvvm.viewmodel.MainViewModel
import com.task.newsfeedapp.mvvm.viewmodel.RoomViewModel
import com.task.newsfeedapp.network.NetworkClient
import com.task.newsfeedapp.utils.Utils.api_key
import com.task.newsfeedapp.view.FirebaseMessagingScreen
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController


/**
 * SANTHOSH
 */
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun ArticleScreen(navController: NavHostController
) {
    getDeviceToken()
    FirebaseMessagingScreen()

    val context = LocalContext.current
    var page = 1
    var wasOffline by remember { mutableStateOf(false) }

    val viewModel: ArticleViewModel = viewModel(
        factory = ViewModelFactory(
            ArticleRepo(NetworkClient.apiService, RoomDao.getDatabase(context)), context
        )
    )
    val repo = remember { RoomRepository(RoomDao.getDatabase(context)) }
    val factory = remember { RoomViewModelFactory(repo) }
    val roomViewModel: RoomViewModel = viewModel(factory = factory)
    val isConnected by viewModel.isConnected.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        if (!isConnected){
            if (wasOffline){
                Toast.makeText(context, "You're back online. Refreshing data.", Toast.LENGTH_SHORT).show()
                viewModel.getArticleList(page, api_key)
            }
            wasOffline = false
        } else {
            wasOffline=true
            roomViewModel.getList()
        }

    }
    ArticleList(navController)
}




