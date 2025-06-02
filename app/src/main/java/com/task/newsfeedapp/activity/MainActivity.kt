package com.task.newsfeedapp.activity

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import com.razorpay.PaymentResultListener
import com.task.newsfeedapp.base.BaseViewModel
import com.task.newsfeedapp.base.ComposeBaseActivity
import com.task.newsfeedapp.base.SplashViewModel
import com.task.newsfeedapp.base.component.ActivityComponent
import com.task.newsfeedapp.navigation.MyNavHost
import com.task.newsfeedapp.screens.agora.AgoraChatManager
import com.task.newsfeedapp.ui.theme.NewsFeedAppTheme

/**
 * SANTHOSHKUMAR
 */

class MainActivity : ComposeBaseActivity<SplashViewModel>(),PaymentResultListener {
    private lateinit var chatManager: AgoraChatManager

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//         Initialize Firebase
        try {
            FirebaseApp.initializeApp(this)
        } catch (e: Exception) {
            Log.e("FCM", "Firebase initialization failed", e)
        }

        chatManager = AgoraChatManager(this, "YOUR_AGORA_APP_ID")
        chatManager.initialize()

        chatManager.login("user1", null) {
            if (it) chatManager.joinChannel("test_channel") {}
        }

        // Code that requires API 26+
        setContent {
            NewsFeedAppTheme {
               MyApp(viewModel)
                fetchFCMToken()


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

                // Get new FCM registration token
                val token = task.result

                // Log and show token
                Log.d("FCM", "FCM Token: $token")
//                Toast.makeText(this, "FCM Token: $token", Toast.LENGTH_SHORT).show()
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
fun MyApp(viewModel: BaseViewModel) {
    MyNavHost(viewModel)
}