package com.task.newsfeedapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.task.newsfeedapp.R
import com.task.newsfeedapp.navigation.OnboardingNavigationObject
import androidx.core.net.toUri
import androidx.compose.foundation.clickable

data class CallLog(
    val name: String,
    val peerId: String,
    val date: String,
    val time: String,
    val isIncoming: Boolean,
    val isMissed: Boolean,
    val isVoiceCall: Boolean,
    val imageRes: Int
)

val dummyCallLogs = listOf(
    CallLog("Diyash & Karencheng", "diyash_k", "23 January", "10:53 pm", isIncoming = true, isMissed = false, isVoiceCall = false, R.drawable.profile_pic),
    CallLog("Danlok", "danlok_g", "23 January", "10:35 pm", isIncoming = true, isMissed = false, isVoiceCall = false, R.drawable.profile_pic),
    CallLog("Shiny", "shiny_s", "23 January", "10:33 pm", isIncoming = true, isMissed = false, isVoiceCall = true, R.drawable.profile_pic),
    CallLog("Kayadu", "kayadu_k", "23 January", "10:29 pm", isIncoming = false, isMissed = false, isVoiceCall = false, R.drawable.profile_pic),
    CallLog("Rakesh", "rakesh_r", "23 January", "8:46 pm", isIncoming = false, isMissed = false, isVoiceCall = true, R.drawable.profile_pic),
    CallLog("Maureen", "maureen_m", "23 January", "8:18 pm", isIncoming = true, isMissed = true, isVoiceCall = true, R.drawable.profile_pic),
    CallLog("Deepika", "deepika_d", "23 January", "7:49 pm", isIncoming = false, isMissed = false, isVoiceCall = true, R.drawable.profile_pic),
    CallLog("Santhosh", "santhosh_s", "23 January", "7:49 pm", isIncoming = false, isMissed = false, isVoiceCall = true, R.drawable.profile_pic),
    CallLog("Sheeba", "sheeba_s", "23 January", "7:49 pm", isIncoming = false, isMissed = false, isVoiceCall = true, R.drawable.profile_pic),
    CallLog("Madhu", "madhu_m", "23 January", "7:49 pm", isIncoming = false, isMissed = false, isVoiceCall = true, R.drawable.profile_pic),
)

@Composable
fun CallHistoryScreen(navController: NavHostController) {
    
    fun startCall(userName: String = "Unknown", peerId: String = "unknown", isVideo: Boolean = false) {
        navController.navigate("${OnboardingNavigationObject.VOICE_CALL_SCREEN}/$userName/$peerId/false/$isVideo")
    }

    Scaffold(
        backgroundColor = Color(0xFF0B141B), // Dark WhatsApp-like background
        floatingActionButton = {
            FloatingActionButton(
                onClick = { startCall() },
                backgroundColor = Color(0xFF20C659),
                contentColor = Color.White
            ) {
                Icon(Icons.Default.Call, contentDescription = "New Call")
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Recent",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium
                )
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Filter",
                    tint = Color.Gray,
                    modifier = Modifier.size(24.dp)
                )
            }

            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(dummyCallLogs) { log ->
                    CallLogItem(log) {
                        startCall(log.name, log.peerId, !log.isVoiceCall) 
                    }
                }
            }
        }
    }
}

@Composable
fun CallLogItem(log: CallLog, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = log.imageRes),
            contentDescription = "Avatar",
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = log.name,
                color = Color.White,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                val icon = if (log.isIncoming) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp
                val color = if (log.isMissed) Color.Red else Color.Green
                Icon(
                    imageVector = icon,
                    contentDescription = "Status",
                    tint = color,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "${log.date}, ${log.time}",
                    color = Color.Gray,
                    fontSize = 14.sp
                )
            }
        }

        // Using Call icon for both as placeholder if Videocam is missing
        Icon(
            imageVector = Icons.Default.Call,
            contentDescription = "Call Type",
            tint = Color(0xFF20C659),
            modifier = Modifier.size(24.dp)
        )
    }
}
