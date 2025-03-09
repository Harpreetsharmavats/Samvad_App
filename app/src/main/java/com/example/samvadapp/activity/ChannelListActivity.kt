package com.example.samvadapp.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.tooling.preview.Preview
import io.getstream.chat.android.compose.ui.channels.ChannelsScreen
import io.getstream.chat.android.compose.ui.channels.SearchMode
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.chat.android.models.Filters

class ChannelListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChatTheme {
                ChannelsScreen(

                    title = "Samvad App",
                    isShowingHeader = true,
                    searchMode = SearchMode.Channels,
                    onChannelClick = {  },
                    onBackPressed = { finish() }
                )
            }
        }
    }
}