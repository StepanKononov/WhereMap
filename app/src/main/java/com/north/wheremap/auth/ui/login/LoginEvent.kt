package com.north.wheremap.auth.ui.login

sealed interface LoginEvent {
    data class Error(val error: String) : LoginEvent
    data object LoginSuccess : LoginEvent
}