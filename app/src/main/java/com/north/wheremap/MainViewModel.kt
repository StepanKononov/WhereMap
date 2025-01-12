package com.north.wheremap

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.north.wheremap.core.domain.auth.SessionStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val sessionStorage: SessionStorage
) : ViewModel() {

    private val _state = MutableStateFlow(MainState())
    val state = _state
        .onStart {
            viewModelScope.launch {
                _state.update {
                    it.copy(
                        isCheckingAuth = true
                    )
                }
                _state.update {
                    it.copy(
                        isLoggedIn = sessionStorage.get() != null
                    )
                }
                _state.update {
                    it.copy(
                        isCheckingAuth = false
                    )
                }
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000L), _state.value)
}