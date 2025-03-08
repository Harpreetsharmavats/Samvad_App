package com.example.samvadapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
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

    private val _loadingEvent = MutableLiveData<LoadingEvent>()
     val loadingEvent : LiveData<LoadingEvent>
         get() = _loadingEvent

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
        _loadingEvent.value = LoadingEvent.Loading

        client.connectGuestUser(
            userId = username,
            username = username).enqueue{ result ->
            _loadingEvent.value = LoadingEvent.NotLoading

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
        _loadingEvent.value = LoadingEvent.Loading
        client.connectUser(
            user = user,
            token = token
        ).enqueue{result ->
            _loadingEvent.value = LoadingEvent.NotLoading
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
    sealed class LoadingEvent{
        object Loading : LoadingEvent()
        object NotLoading : LoadingEvent()
    }
}