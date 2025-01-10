package com.north.wheremap.auth.ui.login

import androidx.annotation.StringRes

sealed interface LoginEvent {
    data class Error(@StringRes val error: Int) : LoginEvent
    data object LoginSuccess : LoginEvent
}