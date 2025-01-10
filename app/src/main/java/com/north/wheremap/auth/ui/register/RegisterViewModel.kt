package com.north.wheremap.auth.ui.register

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
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val userDataValidator: UserDataValidator,
    private val repository: AuthRepository
) : ViewModel() {

    private val _state = MutableStateFlow(RegisterState())

    val state = _state
        .onStart { startObserveUserData() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000L), _state.value)

    private val eventChannel = Channel<RegisterEvent>()
    val events = eventChannel.receiveAsFlow()


    fun onAction(action: RegisterAction) {
        when (action) {
            RegisterAction.OnRegisterClick -> register()
            RegisterAction.OnTogglePasswordVisibilityClick -> {
                _state.update {
                    it.copy(
                        isPasswordVisible = !_state.value.isPasswordVisible
                    )
                }
            }

            RegisterAction.OnLoginClick -> {
                viewModelScope.launch {
                    eventChannel.send(RegisterEvent.GoToLogin)
                }
            }
        }
    }

    private fun startObserveUserData() {
        snapshotFlow { _state.value.email.text }
            .onEach { email ->
                val isValidEmail = userDataValidator.isValidEmail(email.toString())
                _state.update {
                    it.copy(
                        isEmailValid = isValidEmail,
                        canRegister = isValidEmail && _state.value.passwordValidationState.isValidPassword
                                && !_state.value.isRegistering
                    )
                }
            }
            .launchIn(viewModelScope)

        snapshotFlow { _state.value.password.text }
            .onEach { password ->
                val passwordValidationState =
                    userDataValidator.validatePassword(password.toString())

                _state.update {
                    it.copy(
                        passwordValidationState = passwordValidationState,
                        canRegister = state.value.isEmailValid && passwordValidationState.isValidPassword
                                && !state.value.isRegistering
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    private fun register() {
        viewModelScope.launch {
            _state.update { it.copy(isRegistering = true) }
            val result = repository.register(
                email = state.value.email.text.toString().trim(),
                password = state.value.password.text.toString()
            )
            _state.update { it.copy(isRegistering = true) }

            when (result) {
                is Result.Error -> {
                    if (result.error == DataError.Network.CONFLICT) {
                        eventChannel.send(
                            RegisterEvent.Error(R.string.error_email_exists)
                        )
                    } else {
                        eventChannel.send(RegisterEvent.Error(result.error.asStringRes()))
                    }
                }

                is Result.Success -> {
                    eventChannel.send(RegisterEvent.RegistrationSuccess)
                }
            }
        }
    }
}