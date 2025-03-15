package com.example.samvadapp.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material3.Button
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.example.samvadapp.viewmodel.ChannelViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.getstream.chat.android.compose.ui.channels.ChannelsScreen
import io.getstream.chat.android.compose.ui.channels.SearchMode
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.chat.android.compose.viewmodel.channels.ChannelViewModelFactory
import io.getstream.chat.android.models.Filters

@AndroidEntryPoint
class ChannelListActivity : ComponentActivity() {
    val viewModel : ChannelViewModel by viewModels()
    private val factory by lazy {
        ChannelViewModelFactory(
            filters = Filters.`in`(
                "type",
                listOf("gaming", "messaging", "commerce", "team", "livestream")
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        subscribeToEvent()

        setContent {
            ChatTheme {
                var showDailog : Boolean by remember {
                    mutableStateOf(false)
                }
                if (showDailog){
                    CreateChannelDailog(
                        dismiss = { channelName ->
                            viewModel.createChannel(channelName)
                            showDailog = false
                        }
                    )
                }
                ChannelsScreen(
                    title = "Samvad App",
                    viewModelFactory = factory,
                    searchMode = SearchMode.Channels,
                    onChannelClick = {
                        startActivity(MessagesActivity.getIntent(this, channelId = it.cid))
                    },
                    onBackPressed = { finish()},
                    onHeaderActionClick = {
                        showDailog = true
                    },

                )
            }
        }
    }


    @Composable
    fun CreateChannelDailog(modifier: Modifier = Modifier,dismiss : (String) -> Unit) {
        var channelName by remember {
            mutableStateOf("")
        }
        AlertDialog(onDismissRequest = {dismiss(channelName) },
            title = {
                Text(text = "Enter Channel Name")
            },
            text = {
                TextField(
                    value = channelName,
                    onValueChange = { channelName = it },
                    shape = TextFieldDefaults.outlinedShape,
                )
            },
            buttons = {
                Row(
                    modifier = Modifier.padding(all = 6.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = { dismiss(channelName)},
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Text(text = "Create Channel")

                    }

                }
            }

        )

    }
    private fun subscribeToEvent(){
        lifecycleScope.launchWhenStarted {
            viewModel.createChannelEvent.collect{
                when(it){
                    is ChannelViewModel.CreateChannelEvent.Error -> {
                        val errorMessage = it.error
                        showToast(errorMessage)
                    }
                    ChannelViewModel.CreateChannelEvent.Success ->{
                      showToast("Channel Created!")
                    }
                }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()


    }


}