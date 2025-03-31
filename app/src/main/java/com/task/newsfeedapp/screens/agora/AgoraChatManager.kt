package com.task.newsfeedapp.screens.agora

import android.content.Context
import io.agora.rtm.ErrorInfo
import io.agora.rtm.ResultCallback
import io.agora.rtm.RtmChannel
import io.agora.rtm.RtmChannelAttribute
import io.agora.rtm.RtmChannelListener
import io.agora.rtm.RtmChannelMember
import io.agora.rtm.RtmClient
import io.agora.rtm.RtmClientListener
import io.agora.rtm.RtmFileMessage
import io.agora.rtm.RtmImageMessage
import io.agora.rtm.RtmMediaOperationProgress
import io.agora.rtm.RtmMessage

class AgoraChatManager(private val context: Context, private val appId: String) {
    private var rtmClient: RtmClient? = null
    private var rtmChannel: RtmChannel? = null

    fun initialize() {
        try {
            rtmClient = RtmClient.createInstance(context, appId, object : RtmClientListener {
                override fun onConnectionStateChanged(p0: Int, p1: Int) {
                    TODO("Not yet implemented")
                }

                override fun onMessageReceived(p0: RtmMessage?, p1: String?) {
                    TODO("Not yet implemented")
                }

                override fun onImageMessageReceivedFromPeer(p0: RtmImageMessage?, p1: String?) {
                    TODO("Not yet implemented")
                }

                override fun onFileMessageReceivedFromPeer(p0: RtmFileMessage?, p1: String?) {
                    TODO("Not yet implemented")
                }

                override fun onMediaUploadingProgress(p0: RtmMediaOperationProgress?, p1: Long) {
                    TODO("Not yet implemented")
                }

                override fun onMediaDownloadingProgress(p0: RtmMediaOperationProgress?, p1: Long) {
                    TODO("Not yet implemented")
                }

                override fun onTokenExpired() {
                    TODO("Not yet implemented")
                }

                override fun onTokenPrivilegeWillExpire() {
                    TODO("Not yet implemented")
                }

                override fun onPeersOnlineStatusChanged(p0: MutableMap<String, Int>?) {
                    TODO("Not yet implemented")
                }

            })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun login(userId: String, token: String?, callback: (Boolean) -> Unit) {
        rtmClient?.login(token, userId, object : ResultCallback<Void> {
            override fun onSuccess(responseInfo: Void?) { callback(true) }
            override fun onFailure(errorInfo: ErrorInfo?) { callback(false) }
        })
    }

    fun joinChannel(channelId: String, callback: (Boolean) -> Unit) {
        rtmChannel = rtmClient?.createChannel(channelId, object : RtmChannelListener {
            override fun onMemberCountUpdated(p0: Int) {
                TODO("Not yet implemented")
            }

            override fun onAttributesUpdated(p0: MutableList<RtmChannelAttribute>?) {
                TODO("Not yet implemented")
            }

            override fun onMessageReceived(p0: RtmMessage?, p1: RtmChannelMember?) {
                TODO("Not yet implemented")
            }

            override fun onImageMessageReceived(p0: RtmImageMessage?, p1: RtmChannelMember?) {
                TODO("Not yet implemented")
            }

            override fun onFileMessageReceived(p0: RtmFileMessage?, p1: RtmChannelMember?) {
                TODO("Not yet implemented")
            }

            override fun onMemberJoined(p0: RtmChannelMember?) {
                TODO("Not yet implemented")
            }

            override fun onMemberLeft(p0: RtmChannelMember?) {
                TODO("Not yet implemented")
            }

        })
        rtmChannel?.join(object : ResultCallback<Void> {
            override fun onSuccess(responseInfo: Void?) { callback(true) }
            override fun onFailure(errorInfo: ErrorInfo?) { callback(false) }
        })
    }

    fun sendMessage(message: String, callback: (Boolean) -> Unit) {
        rtmChannel?.sendMessage(rtmClient?.createMessage(message), object : ResultCallback<Void> {
            override fun onSuccess(responseInfo: Void?) { callback(true) }
            override fun onFailure(errorInfo: ErrorInfo?) { callback(false) }
        })
    }
}
