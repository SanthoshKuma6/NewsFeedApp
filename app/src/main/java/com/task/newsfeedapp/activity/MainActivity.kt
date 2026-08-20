package com.task.newsfeedapp.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import com.razorpay.PaymentResultListener
import com.task.newsfeedapp.base.BaseViewModel
import com.task.newsfeedapp.base.ComposeBaseActivity
import com.task.newsfeedapp.base.SplashViewModel
import com.task.newsfeedapp.base.component.ActivityComponent
import com.task.newsfeedapp.component.NoInternetDialog
import com.task.newsfeedapp.mvvm.repository.ChatRepository
import com.task.newsfeedapp.mvvm.repository.ProfileRepository
import com.task.newsfeedapp.navigation.MyNavHost
import com.task.newsfeedapp.screens.agora.AgoraChatManager
import com.task.newsfeedapp.screens.agora.AgoraRTCManager
import com.task.newsfeedapp.ui.theme.NewsFeedAppTheme
import javax.inject.Inject
import com.task.newsfeedapp.utils.NetworkMonitor

import com.task.newsfeedapp.utils.Utils

/**
 * SANTHOSHKUMAR
 */

class MainActivity : ComposeBaseActivity<SplashViewModel>(), PaymentResultListener {
    @Inject
    lateinit var chatManager: AgoraChatManager
    @Inject
    lateinit var rtcManager: AgoraRTCManager
    @Inject
    lateinit var chatRepository: ChatRepository
    @Inject
    lateinit var profileRepository: ProfileRepository

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            FirebaseApp.initializeApp(this)
        } catch (e: Exception) {
            Log.e("FCM", "Firebase initialization failed", e)
        }

        chatManager.initialize()

        val currentUser = com.google.firebase.auth.FirebaseAuth.getInstance().currentUser
        currentUser?.email?.let { email ->
            val sanitizedId = Utils.sanitizeEmail(email)
            chatManager.login(sanitizedId, null) { success ->
                if (success) {
                    Log.d("AgoraRTM", "Logged in as $sanitizedId")
                }
            }
        }

        handleIntent(intent)

        setContent {
            val networkMonitor = remember { NetworkMonitor(this) }
            val isConnected by networkMonitor.isConnected.collectAsState()

            NewsFeedAppTheme {
                MyApp(viewModel, chatManager, rtcManager, chatRepository, profileRepository)
                if (!isConnected) {
                    NoInternetDialog {
                        // The NetworkMonitor automatically updates isConnected state
                    }
                }
                fetchFCMToken()
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent?) {
        intent?.let {
            val senderId = it.getStringExtra("INCOMING_CALL_SENDER_ID")
            // val senderName = it.getStringExtra("INCOMING_CALL_SENDER_NAME")
            // val isVideo = it.getBooleanExtra("INCOMING_CALL_IS_VIDEO", false)

            if (senderId != null) {
                // Future: Trigger navigation to call screen from here
            }
        }
    }

    override fun injectDependencies(activityComponent: ActivityComponent) {
        activityComponent.inject(this@MainActivity)
    }

    private fun fetchFCMToken() {
        try {
            FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w("FCM", "Fetching FCM registration token failed", task.exception)
                    return@addOnCompleteListener
                }
                val token = task.result
                Log.d("FCM", "FCM Token: $token")
            }
        } catch (e: Exception) {
            Log.e("FCM", "Error fetching FCM token", e)
        }
    }

    override fun onPaymentSuccess(razorpayPaymentId: String?) {
        Toast.makeText(this, "Payment Successful: $razorpayPaymentId", Toast.LENGTH_LONG).show()
    }

    override fun onPaymentError(code: Int, description: String?) {
        Toast.makeText(this, "Payment Failed: $description", Toast.LENGTH_LONG).show()
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun MyApp(
    viewModel: BaseViewModel,
    chatManager: AgoraChatManager,
    rtcManager: AgoraRTCManager,
    chatRepository: ChatRepository,
    profileRepository: ProfileRepository
) {
    MyNavHost(viewModel, chatManager, rtcManager, chatRepository, profileRepository)
}
