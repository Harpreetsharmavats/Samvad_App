package com.example.samvadapp.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.getstream.chat.android.client.ChatClient
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val client : ChatClient
): ViewModel() {
}