package com.north.wheremap.auth.ui.intro

sealed interface IntroAction {
    data object OnSignInClick : IntroAction
    data object OnSignUpClick : IntroAction
}