package com.north.wheremap.auth.ui.login

import android.util.Log
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.north.wheremap.R
import com.north.wheremap.auth.domain.AuthRepository
import com.north.wheremap.auth.domain.UserDataValidator
import com.north.wheremap.core.domain.utils.DataError
import com.north.wheremap.core.domain.utils.Result
import com.north.wheremap.core.ui.asStringRes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userDataValidator: UserDataValidator
) : ViewModel() {


    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()

    private val eventChannel = Channel<LoginEvent>()
    val events = eventChannel.receiveAsFlow()

    init {
        combine(
            snapshotFlow { state.value.email.text },
            snapshotFlow { state.value.password.text }
        ) { email, password ->

            Log.v("===>", "email = $email, password = $password")
            _state.update {
                it.copy(
                    canLogin = userDataValidator.isValidEmail(
                        email = email.toString().trim()
                    ) && password.isNotEmpty()
                )
            }
        }.launchIn(viewModelScope)
    }

    fun onAction(action: LoginAction) {
        when (action) {
            LoginAction.OnLoginClick -> login()

            LoginAction.OnTogglePasswordVisibility -> {
                _state.update {
                    it.copy(
                        isPasswordVisible = !_state.value.isPasswordVisible
                    )
                }
            }

            else -> Unit
        }
    }

    private fun login() {
        viewModelScope.launch {
            _state.update {
                it.copy(isLoggingIn = true)
            }
            val result = authRepository.login(
                email = state.value.email.text.toString().trim(),
                password = state.value.password.text.toString()
            )
            _state.update {
                it.copy(isLoggingIn = false)
            }
            when (result) {
                is Result.Error -> {
                    if (result.error == DataError.Network.UNAUTHORIZED) {
                        eventChannel.send(
                            LoginEvent.Error(R.string.error_email_password_incorrect)
                        )
                    } else {
                        eventChannel.send(LoginEvent.Error(result.error.asStringRes()))
                    }
                }

                is Result.Success -> {
                    eventChannel.send(LoginEvent.LoginSuccess)
                }
            }
        }
    }
}