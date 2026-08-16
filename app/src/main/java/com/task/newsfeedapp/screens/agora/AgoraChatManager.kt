package com.task.newsfeedapp.screens.agora

import android.content.Context
import android.util.Log
import io.agora.rtm.*

class AgoraChatManager(private val context: Context, private val appId: String) {
    private var rtmClient: RtmClient? = null
    private var rtmChannel: RtmChannel? = null
    
    var onMessageReceived: ((String, String) -> Unit)? = null // (senderId, message)

    fun initialize() {
        if (rtmClient != null) return
        
        try {
            rtmClient = RtmClient.createInstance(context, appId, object : RtmClientListener {
                override fun onConnectionStateChanged(state: Int, reason: Int) {
                    Log.d("AgoraRTM", "Connection state changed to $state, reason $reason")
                }

                override fun onMessageReceived(message: RtmMessage?, peerId: String?) {
                    Log.d("AgoraRTM", "Message received from $peerId: ${message?.text}")
                    if (message != null && peerId != null) {
                        onMessageReceived?.invoke(peerId, message.text)
                    }
                }

                override fun onImageMessageReceivedFromPeer(p0: RtmImageMessage?, p1: String?) {}
                override fun onFileMessageReceivedFromPeer(p0: RtmFileMessage?, p1: String?) {}
                override fun onMediaUploadingProgress(p0: RtmMediaOperationProgress?, p1: Long) {}
                override fun onMediaDownloadingProgress(p0: RtmMediaOperationProgress?, p1: Long) {}
                override fun onTokenExpired() {}
                override fun onTokenPrivilegeWillExpire() {}
                override fun onPeersOnlineStatusChanged(p0: MutableMap<String, Int>?) {}
            })
        } catch (e: Exception) {
            Log.e("AgoraRTM", "Initialization failed", e)
        }
    }

    fun login(userId: String, token: String?, callback: (Boolean) -> Unit) {
        rtmClient?.login(token, userId, object : ResultCallback<Void> {
            override fun onSuccess(responseInfo: Void?) {
                Log.d("AgoraRTM", "Login success for $userId")
                callback(true)
            }
            override fun onFailure(errorInfo: ErrorInfo?) {
                Log.e("AgoraRTM", "Login failed: ${errorInfo?.toString()}")
                callback(false)
            }
        })
    }

    fun sendPeerMessage(peerId: String, message: String, callback: (Boolean) -> Unit) {
        val rtmMessage = rtmClient?.createMessage(message)
        rtmClient?.sendMessageToPeer(peerId, rtmMessage, SendMessageOptions(), object : ResultCallback<Void> {
            override fun onSuccess(p0: Void?) {
                Log.d("AgoraRTM", "Message sent to $peerId")
                callback(true)
            }
            override fun onFailure(errorInfo: ErrorInfo?) {
                Log.e("AgoraRTM", "Message failed to $peerId: ${errorInfo?.toString()}")
                callback(false)
            }
        })
    }

    fun logout() {
        rtmClient?.logout(null)
    }
    
    fun release() {
        rtmClient?.release()
        rtmClient = null
    }
}
