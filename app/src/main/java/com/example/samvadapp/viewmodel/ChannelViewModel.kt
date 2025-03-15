package com.example.samvadapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.getstream.chat.android.client.ChatClient
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ChannelViewModel @Inject constructor(
    private val client: ChatClient
) : ViewModel() {
    private val _createChannelEvent = MutableSharedFlow<CreateChannelEvent>()
    val createChannelEvent = _createChannelEvent.asSharedFlow()

    fun createChannel(channelName: String, channelType: String = "messaging") {
        val trimmedChannelName = channelName.trim()
        val channelId = UUID.randomUUID().toString()

        viewModelScope.launch {
            if (trimmedChannelName.isEmpty()) {
                _createChannelEvent.emit(CreateChannelEvent.Error("The channel name Can't be empty"))
                return@launch
            }

            client.createChannel(
                channelType = channelType,
                channelId = channelId,
                memberIds = emptyList(),
                extraData = mapOf(
                    "name" to trimmedChannelName,
                    "image" to "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.bulksms.com%2Ffeatures%2Fsend-sms-messages-from-your-mobile-to-group.htm&psig=AOvVaw3-WjXAiXf_Q342XVc7c32H&ust=1742101767838000&source=images&cd=vfe&opi=89978449&ved=0CBQQjRxqFwoTCIiayPqoi4wDFQAAAAAdAAAAABAR"
                )
            ).enqueue{
                if (it.isSuccess){
                    viewModelScope.launch {
                        _createChannelEvent.emit(CreateChannelEvent.Success)
                    }
                }else{
                    viewModelScope.launch {
                        _createChannelEvent.emit(CreateChannelEvent.Error(it.errorOrNull()?.message ?: "Unknown error"))
                    }
                }
            }
        }
    }

    sealed class CreateChannelEvent {
        data class Error(val error: String) : CreateChannelEvent()
        object Success : CreateChannelEvent()
    }
}