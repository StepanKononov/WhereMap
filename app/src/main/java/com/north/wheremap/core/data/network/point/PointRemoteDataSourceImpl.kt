package com.north.wheremap.core.data.network.point

import com.north.wheremap.core.data.networking.get
import com.north.wheremap.core.data.networking.post
import com.north.wheremap.core.domain.point.Point
import com.north.wheremap.core.domain.point.PointRemoteDataSource
import com.north.wheremap.core.domain.utils.DataError
import com.north.wheremap.core.domain.utils.Result
import com.north.wheremap.core.domain.utils.map
import io.ktor.client.HttpClient
import javax.inject.Inject

class PointRemoteDataSourceImpl @Inject constructor(
    private val httpClient: HttpClient,
) : PointRemoteDataSource {

    override suspend fun getPointsInCollection(collectionId: String): Result<List<Point>, DataError.Network> {
        return httpClient.get<List<PointDto>>(
            route = "/points/$collectionId",
        ).map { pointDtos ->
            pointDtos.map { it.toPoint() }
        }
    }

    override suspend fun upsertPoint(point: Point): Result<Point, DataError.Network> {
        return httpClient.post<Point, PointDto>(
            route = "/points",
            body = point
        ).map { it.toPoint() }
    }

    override suspend fun getUserPoints(): Result<List<Point>, DataError.Network> {
        return httpClient.get<List<PointDto>>(
            route = "/points",
        ).map { pointDtos ->
            pointDtos.map { it.toPoint() }
        }
    }
}