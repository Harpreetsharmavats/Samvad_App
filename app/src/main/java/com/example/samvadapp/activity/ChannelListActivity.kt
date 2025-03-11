package com.example.samvadapp.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import io.getstream.chat.android.compose.ui.channels.ChannelsScreen
import io.getstream.chat.android.compose.ui.channels.SearchMode
import io.getstream.chat.android.compose.ui.theme.ChatTheme

class ChannelListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChatTheme {
               ChannelsScreen(
                   title = "Samvad App",
                   searchMode = SearchMode.Channels,
                   onChannelClick = {
                       startActivity(MessagesActivity.getIntent(this, channelId = it.cid))
                   },
                   onBackPressed = { finish()  }

               )
            }
        }
    }


}