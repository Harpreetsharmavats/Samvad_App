package com.example.samvadapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.samvadapp.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.models.User
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val client : ChatClient
): ViewModel() {

    private val _loginEvent = MutableSharedFlow<LogInEvent>()
    val logInEvent = _loginEvent.asSharedFlow()

    private fun validUserName(username : String) : Boolean{
        return username.length > Constants.MIN_USERNAME_LENGTH
    }

    fun loginUser( username: String , token: String? = null){
        val trimmedUsername = username.trim()
        viewModelScope.launch {
            if (validUserName(trimmedUsername) && token != null) {
                loginRegisteredUser(trimmedUsername, token)

            }else if (validUserName(trimmedUsername) && token == null){
                loginAsGuest(trimmedUsername)

            }else{
                _loginEvent.emit(LogInEvent.ErrorInputTooShort)
            }
        }
    }

    private fun loginAsGuest(username: String) {
        client.connectGuestUser(
            userId = username,
            username = username).enqueue{ result ->
                if (result.isSuccess){
                   viewModelScope.launch {
                       _loginEvent.emit(LogInEvent.SuccessLogin)
                   }
                }else{
                    viewModelScope.launch {
                        _loginEvent.emit(LogInEvent.ErrorLogin(
                            result.errorOrNull()?.message ?: "Unknown Error"))
                    }
                }
        }
    }

    private fun loginRegisteredUser(username: String, token: String) {
        val user = User( id = username, name = username)

        client.connectUser(
            user = user,
            token = token
        ).enqueue{result ->
            if (result.isSuccess){
                viewModelScope.launch {
                    _loginEvent.emit(LogInEvent.SuccessLogin)
                }
            }else{
                viewModelScope.launch {
                    _loginEvent.emit(LogInEvent.ErrorLogin(
                        result.errorOrNull()?.message ?: "Unknown Error"))
                }
            }
        }
    }

    sealed class LogInEvent{
        object ErrorInputTooShort : LogInEvent()
        data class ErrorLogin(val error : String) : LogInEvent()
        object SuccessLogin : LogInEvent()
    }
}