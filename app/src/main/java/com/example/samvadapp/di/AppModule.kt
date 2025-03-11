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
import io.getstream.chat.android.offline.plugin.factory.StreamOfflinePluginFactory
import io.getstream.chat.android.state.plugin.config.StatePluginConfig
import io.getstream.chat.android.state.plugin.factory.StreamStatePluginFactory
import io.getstream.chat.android.state.plugin.internal.StatePlugin
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideChatClient(
        @ApplicationContext context: Context
    ): ChatClient {
        val statePluginFactory = StreamStatePluginFactory(
            config = StatePluginConfig(),
            appContext = context
        )

        val offlinePluginFactory = StreamOfflinePluginFactory(
            appContext = context
        )

        return ChatClient.Builder(context.getString(R.string.api_key), context)
            .withPlugins(statePluginFactory, offlinePluginFactory)
            .logLevel(ChatLogLevel.ALL) // Debugging logs
            .build()
    }
}
