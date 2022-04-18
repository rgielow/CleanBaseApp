package com.gielow.cleanbaseapp.feature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gielow.cleanbaseapp.commons.viewModel.ChannelEventSenderImpl
import com.gielow.cleanbaseapp.commons.viewModel.EventSender
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel(),
    EventSender<HomeViewModel.Navigation> by ChannelEventSenderImpl() {

    fun navigate(navigation: Navigation) {
        viewModelScope.sendEvent(navigation)
    }

    sealed class Navigation(
        val route: String = EMPTY_STRING,
        val popStack: Boolean = false
    ) {
        object Home : Navigation(route = "home")
    }

    companion object {
        private const val EMPTY_STRING = ""
    }
}