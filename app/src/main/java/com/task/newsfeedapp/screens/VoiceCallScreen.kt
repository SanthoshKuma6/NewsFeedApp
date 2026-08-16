package com.task.newsfeedapp.screens

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.task.newsfeedapp.R
import com.task.newsfeedapp.screens.agora.AgoraChatManager
import com.task.newsfeedapp.screens.agora.AgoraRTCManager

enum class CallState {
    IDLE, RINGING, CONNECTING, ACTIVE, ENDED
}

@Composable
fun VoiceCallScreen(
    navController: NavController,
    userName: String,
    peerId: String,
    chatManager: AgoraChatManager,
    rtcManager: AgoraRTCManager,
    isIncoming: Boolean = false,
    isVideo: Boolean = false
) {
    var callState by remember { mutableStateOf(if (isIncoming) CallState.RINGING else CallState.CONNECTING) }
    var isMuted by remember { mutableStateOf(false) }
    var isSpeakerOn by remember { mutableStateOf(isVideo) } // Default speaker on for video
    var isVideoEnabled by remember { mutableStateOf(isVideo) }
    var remoteUid by remember { mutableStateOf<Int?>(null) }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            rtcManager.initialize()
            if (!isIncoming) {
                // Outbound signaling using peerId
                chatManager.sendPeerMessage(peerId, AgoraChatManager.SIGNAL_CALL_INVITE) { }
                rtcManager.joinChannel(peerId) // Use peerId as channelName for 1:1
            }
        } else {
            navController.popBackStack()
        }
    }

    LaunchedEffect(Unit) {
        permissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
        
        chatManager.onCallSignalReceived = { senderId, type ->
            if (senderId == peerId) {
                when (type) {
                    AgoraChatManager.SIGNAL_CALL_ACCEPT -> {
                        callState = CallState.ACTIVE
                    }
                    AgoraChatManager.SIGNAL_CALL_REJECT, AgoraChatManager.SIGNAL_CALL_END -> {
                        callState = CallState.ENDED
                        rtcManager.leaveChannel()
                        navController.popBackStack()
                    }
                }
            }
        }

        rtcManager.onUserJoined = { uid ->
            callState = CallState.ACTIVE
            remoteUid = uid
        }

        rtcManager.onUserOffline = { _ ->
            callState = CallState.ENDED
            remoteUid = null
            rtcManager.leaveChannel()
            navController.popBackStack()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0B141B))
    ) {
        if (callState == CallState.ACTIVE && isVideoEnabled) {
            // Remote Video
            if (remoteUid != null) {
                AndroidView(
                    factory = { ctx ->
                        android.widget.FrameLayout(ctx).apply {
                            rtcManager.setupRemoteVideo(this, remoteUid!!)
                        }
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }

            // Local Video Preview
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 80.dp, end = 16.dp)
                    .size(120.dp, 160.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.Black)
            ) {
                AndroidView(
                    factory = { ctx ->
                        android.widget.FrameLayout(ctx).apply {
                            rtcManager.setupLocalVideo(this)
                        }
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (!isVideoEnabled || callState != CallState.ACTIVE) {
                Spacer(modifier = Modifier.weight(1f))

                Image(
                    painter = painterResource(id = R.drawable.profile_pic),
                    contentDescription = null,
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = userName,
                    color = Color.White,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = when (callState) {
                        CallState.RINGING -> "Incoming Call..."
                        CallState.CONNECTING -> "Connecting..."
                        CallState.ACTIVE -> "Ongoing Call"
                        else -> ""
                    },
                    color = Color.Gray,
                    fontSize = 18.sp
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            if (callState == CallState.RINGING) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 64.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    // Reject
                    FloatingActionButton(
                        onClick = {
                            chatManager.sendPeerMessage(peerId, AgoraChatManager.SIGNAL_CALL_REJECT) { }
                            navController.popBackStack()
                        },
                        containerColor = Color.Red,
                        contentColor = Color.White,
                        shape = CircleShape
                    ) {
                        Icon(Icons.Default.Close, contentDescription = "Reject")
                    }

                    // Accept
                    FloatingActionButton(
                        onClick = {
                            chatManager.sendPeerMessage(peerId, AgoraChatManager.SIGNAL_CALL_ACCEPT) { }
                            rtcManager.joinChannel(peerId)
                            callState = CallState.ACTIVE
                        },
                        containerColor = Color.Green,
                        contentColor = Color.White,
                        shape = CircleShape
                    ) {
                        Icon(Icons.Default.Call, contentDescription = "Accept")
                    }
                }
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 64.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Mute Toggle
                    IconButton(onClick = {
                        isMuted = !isMuted
                        rtcManager.muteLocalAudio(isMuted)
                    }) {
                        Icon(
                            imageVector = if (isMuted) Icons.Default.Warning else Icons.Default.Notifications,
                            contentDescription = "Mute",
                            tint = Color.White,
                            modifier = Modifier.size(32.dp)
                        )
                    }

                    // End Call
                    FloatingActionButton(
                        onClick = {
                            chatManager.sendPeerMessage(peerId, AgoraChatManager.SIGNAL_CALL_END) { }
                            rtcManager.leaveChannel()
                            navController.popBackStack()
                        },
                        containerColor = Color.Red,
                        contentColor = Color.White,
                        shape = CircleShape,
                        modifier = Modifier.size(72.dp)
                    ) {
                        Icon(Icons.Default.Close, contentDescription = "End Call", modifier = Modifier.size(36.dp))
                    }

                    // Switch Camera (only for video)
                    if (isVideoEnabled) {
                        IconButton(onClick = { rtcManager.switchCamera() }) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = "Switch Camera",
                                tint = Color.White,
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    } else {
                        // Speaker Toggle (for voice)
                        IconButton(onClick = {
                            isSpeakerOn = !isSpeakerOn
                            rtcManager.setEnableSpeakerphone(isSpeakerOn)
                        }) {
                            Icon(
                                imageVector = Icons.Default.Notifications,
                                contentDescription = "Speaker",
                                tint = if (isSpeakerOn) Color.Green else Color.White,
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
