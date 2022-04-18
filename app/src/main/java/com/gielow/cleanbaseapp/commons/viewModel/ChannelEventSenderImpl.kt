package com.gielow.cleanbaseapp.commons.viewModel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class ChannelEventSenderImpl<T> : EventSender<T> {
    private val eventsChannel = Channel<T>(Channel.BUFFERED)
    override val eventsFlow: Flow<T> = eventsChannel.receiveAsFlow()
    override fun CoroutineScope.sendEvent(event: T): Job = launch {
        eventsChannel.send(event)
    }
}