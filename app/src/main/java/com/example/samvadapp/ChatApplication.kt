package com.example.samvadapp

import android.app.Application
import android.util.Log
import dagger.hilt.android.HiltAndroidApp
import io.getstream.chat.android.client.ChatClient

private val ChatClient.isInitialized: Boolean
    get() {
        TODO("Not yet implemented")
    }

@HiltAndroidApp
class ChatApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        if (!ChatClient.instance().isInitialized) {
            Log.e("StreamChat", "ChatClient is NOT initialized!")
        } else {
            Log.d("StreamChat", "ChatClient initialized successfully!")
        }
    }
}