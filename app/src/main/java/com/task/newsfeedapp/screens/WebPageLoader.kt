package com.task.newsfeedapp.screens

import android.annotation.SuppressLint
import android.webkit.WebView
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun WebPageLoader(url: String) {
    AndroidView(factory = { context ->
        WebView(context).apply {
            settings.javaScriptEnabled = true
            loadUrl(url)
        }
    })
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun WebViewScreen() {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Web Page") })
        }
    ) {
        WebPageLoader(url = "https://www.example.com")
    }
}

@Preview
@Composable
fun PreviewWebView() {
    WebViewScreen()
}
