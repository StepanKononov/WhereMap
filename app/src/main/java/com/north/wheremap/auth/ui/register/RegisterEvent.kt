package com.north.wheremap.auth.ui.register

import androidx.annotation.StringRes

sealed interface RegisterEvent {
    data object RegistrationSuccess : RegisterEvent
    data object GoToLogin : RegisterEvent
    data class Error(@StringRes val error: Int) : RegisterEvent
}