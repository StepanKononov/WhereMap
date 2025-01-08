package com.north.wheremap.auth.ui.register

sealed interface RegisterEvent {
    data object RegistrationSuccess: RegisterEvent
    data class Error(val error: String): RegisterEvent
}