package com.north.wheremap.auth.data

import kotlinx.serialization.Serializable

@Serializable
class RegisterResponse(
    val accessToken: String,
    val refreshToken: String,
    val userId: String
)