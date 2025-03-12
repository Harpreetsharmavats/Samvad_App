package com.example.samvadapp.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import io.getstream.chat.android.compose.ui.channels.ChannelsScreen
import io.getstream.chat.android.compose.ui.channels.SearchMode
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.chat.android.compose.viewmodel.channels.ChannelViewModelFactory
import io.getstream.chat.android.models.Filters

class ChannelListActivity : ComponentActivity() {
    private val factory by lazy {
        ChannelViewModelFactory(
            filters = Filters.`in`("type", listOf("gaming", "messaging", "commerce", "team", "livestream"))
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ChatTheme {
                ChannelsScreen(
                    title = "Samvad App",
                    viewModelFactory = factory,
                    searchMode = SearchMode.Channels,
                    onChannelClick = {
                        startActivity(MessagesActivity.getIntent(this, channelId = it.cid))
                    },
                    onBackPressed = { finish() }

                )
            }
        }
    }



}