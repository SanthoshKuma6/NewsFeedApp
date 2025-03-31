package com.task.newsfeedapp.screens.payment

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.razorpay.Checkout
import org.json.JSONObject

fun StartPayment(context: Context, amount: Int) {
    val activity = context as Activity
    val checkout = Checkout()
    checkout.setKeyID("YOUR_RAZORPAY_KEY") // Replace with your actual Razorpay API Key

    try {
        val options = JSONObject()
        options.put("name", "Your App Name")
        options.put("description", "Payment for services")
        options.put("currency", "INR")
        options.put("amount", amount * 100) // Amount in paise (₹1 = 100 paise)

        val prefill = JSONObject()
        prefill.put("email", "user@example.com")
        prefill.put("contact", "9876543210")
        options.put("prefill", prefill)

        checkout.open(activity, options)
    } catch (e: Exception) {
        Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_LONG).show()
    }
}


@Composable
fun PaymentScreen(context: Context) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Button(onClick = { StartPayment(context, 30) }) {

            Text(text = "Pay ₹30")
        }
    }
}
