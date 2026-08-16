package com.task.newsfeedapp.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.scottyab.rootbeer.RootBeer
import com.task.newsfeedapp.utils.Utils
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
/**
 * SANTHOSHKUMAR
 */

@SuppressLint("CustomSplashScreen")
class SplashActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val showDialog = remember { mutableStateOf(false) }
            val dialogMessage = remember { mutableStateOf("") }

            LaunchedEffect(Unit) {
                checkCodeTampering(
                    onTampered = {
                        dialogMessage.value = "Detected App Code Modification"
                        showDialog.value = true
                    },
                    onRooted = {
                        dialogMessage.value =
                            "You are a root user, to access the app use another device"
                        showDialog.value = true
                    },


                    )
            }

            Surface(
                modifier = androidx.compose.ui.Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                if (showDialog.value) {
                    AlertDialog(
                        onDismissRequest = { showDialog.value = false },
                        title = { Text(text = "Info") },
                        text = { Text(text = dialogMessage.value) },
                        confirmButton = {
                            Button(onClick = { finish() }) {
                                Text("OK")
                            }
                        }
                    )
                }
            }
        }
    }

    private fun checkCodeTampering(
        onTampered: () -> Unit,
        onRooted: () -> Unit
    ) {
        val tamperCheck = TamperCheck()
        if (tamperCheck.validateAppSignature(this)) {
            onTampered()
            return
        } else {
            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        val rootBeer = RootBeer(this)
        if (rootBeer.isRooted) {
            onRooted()
            return
        }

    }
}

class TamperCheck {
    private val APP_SIGNATURE = Utils.Saved_Signature

    fun validateAppSignature(context: Context): Boolean {
        val packageInfo: PackageInfo
        try {
            packageInfo = context.packageManager.getPackageInfo(
                context.packageName, PackageManager.GET_SIGNATURES
            )
            for (signature in packageInfo.signatures!!) {
                val sha1 = getSHA1(signature.toByteArray())
                println("DEBUG: App Signature: $sha1, Saved Signature: $APP_SIGNATURE")
                Log.d("TAG", "App Signature: $sha1,")
                Log.d("TAG", "Saved Signature: $APP_SIGNATURE,")

                if (APP_SIGNATURE == sha1) {
                    return true
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }

    private fun getSHA1(byteArray: ByteArray): String {
        return try {
            val digest = MessageDigest.getInstance("SHA-1")
            val hashBytes = digest.digest(byteArray)
            hashBytes.joinToString("") { "%02x".format(it) }
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
            ""
        }
    }
}