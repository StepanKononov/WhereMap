package com.north.wheremap.auth.data

import com.north.wheremap.auth.domain.AuthRepository
import com.north.wheremap.core.data.networking.post
import com.north.wheremap.core.di.AuthSharedPreferences
import com.north.wheremap.core.domain.auth.AuthInfo
import com.north.wheremap.core.domain.auth.SessionStorage
import com.north.wheremap.core.domain.utils.DataError
import com.north.wheremap.core.domain.utils.EmptyResult
import com.north.wheremap.core.domain.utils.Result
import com.north.wheremap.core.domain.utils.asEmptyDataResult
import io.ktor.client.HttpClient
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val httpClient: HttpClient,
    @AuthSharedPreferences
    private val sessionStorage: SessionStorage
) : AuthRepository {

    override suspend fun login(email: String, password: String): EmptyResult<DataError.Network> {
        val result = httpClient.post<LoginRequest, LoginResponse>(
            route = "/login",
            body = LoginRequest(
                email = email,
                password = password
            )
        )
        if (result is Result.Success) {
            sessionStorage.set(
                AuthInfo(
                    accessToken = result.data.accessToken,
                    refreshToken = result.data.refreshToken,
                    userId = result.data.userId
                )
            )
        }
        return result.asEmptyDataResult()
    }

    override suspend fun register(email: String, password: String): EmptyResult<DataError.Network> {
        val result = httpClient.post<RegisterRequest, RegisterResponse>(
            route = "/register",
            body = RegisterRequest(
                email = email,
                password = password
            )
        )
        if (result is Result.Success) {
            sessionStorage.set(
                AuthInfo(
                    accessToken = result.data.accessToken,
                    refreshToken = result.data.refreshToken,
                    userId = result.data.userId
                )
            )
        }
        return result.asEmptyDataResult()
    }
}