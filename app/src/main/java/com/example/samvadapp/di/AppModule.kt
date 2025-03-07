package com.example.samvadapp.di

import android.content.Context
import com.example.samvadapp.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.logger.ChatLogLevel
import io.getstream.chat.android.state.plugin.factory.StreamStatePluginFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    // Provide ChatClient instance
    @Singleton
    @Provides
    fun provideChatClient(
        @ApplicationContext context: Context,

    ): ChatClient {
        return ChatClient.Builder(context.getString(R.string.api_key), context)
            .logLevel(ChatLogLevel.ALL)       // Debugging logs
            .build()
    }
}
