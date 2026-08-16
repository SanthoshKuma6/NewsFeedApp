package com.task.newsfeedapp.screens.agora

import android.content.Context
import android.util.Log
import io.agora.rtc2.Constants
import io.agora.rtc2.IRtcEngineEventHandler
import io.agora.rtc2.RtcEngine
import io.agora.rtc2.RtcEngineConfig

class AgoraRTCManager(private val context: Context, private val appId: String) {
    private var rtcEngine: RtcEngine? = null
    
    var onUserJoined: ((Int) -> Unit)? = null
    var onUserOffline: ((Int) -> Unit)? = null
    var onLeaveChannel: (() -> Unit)? = null

    private val rtcEventHandler = object : IRtcEngineEventHandler() {
        override fun onJoinChannelSuccess(channel: String?, uid: Int, elapsed: Int) {
            Log.d("AgoraRTC", "Join channel success: $channel, uid: $uid")
        }

        override fun onUserJoined(uid: Int, elapsed: Int) {
            Log.d("AgoraRTC", "User joined: $uid")
            onUserJoined?.invoke(uid)
        }

        override fun onUserOffline(uid: Int, reason: Int) {
            Log.d("AgoraRTC", "User offline: $uid")
            onUserOffline?.invoke(uid)
        }

        override fun onLeaveChannel(stats: RtcStats?) {
            Log.d("AgoraRTC", "Leave channel")
            onLeaveChannel?.invoke()
        }
    }

    fun initialize() {
        if (rtcEngine != null) return
        try {
            val config = RtcEngineConfig()
            config.mContext = context
            config.mAppId = appId
            config.mEventHandler = rtcEventHandler
            rtcEngine = RtcEngine.create(config)
            
            // For communication, we use the communication profile
            rtcEngine?.setChannelProfile(Constants.CHANNEL_PROFILE_COMMUNICATION)
            
            // Enable video module
            rtcEngine?.enableVideo()
        } catch (e: Exception) {
            Log.e("AgoraRTC", "Initialization failed", e)
        }
    }

    fun setupLocalVideo(container: android.view.ViewGroup) {
        val surfaceView = android.view.SurfaceView(context)
        container.addView(surfaceView)
        rtcEngine?.setupLocalVideo(io.agora.rtc2.video.VideoCanvas(surfaceView, io.agora.rtc2.video.VideoCanvas.RENDER_MODE_HIDDEN, 0))
    }

    fun setupRemoteVideo(container: android.view.ViewGroup, uid: Int) {
        val surfaceView = android.view.SurfaceView(context)
        container.addView(surfaceView)
        rtcEngine?.setupRemoteVideo(io.agora.rtc2.video.VideoCanvas(surfaceView, io.agora.rtc2.video.VideoCanvas.RENDER_MODE_HIDDEN, uid))
    }

    fun switchCamera() {
        rtcEngine?.switchCamera()
    }

    fun joinChannel(channelName: String, uid: Int = 0, token: String? = null) {
        rtcEngine?.joinChannel(token, channelName, null, uid)
    }

    fun leaveChannel() {
        rtcEngine?.leaveChannel()
    }

    fun muteLocalAudio(mute: Boolean) {
        rtcEngine?.muteLocalAudioStream(mute)
    }

    fun setEnableSpeakerphone(enable: Boolean) {
        rtcEngine?.setEnableSpeakerphone(enable)
    }

    fun release() {
        RtcEngine.destroy()
        rtcEngine = null
    }
}
