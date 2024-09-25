package com.jujiiz.coins.core.di

import com.jujiiz.coins.core.models.BaseRemoteResponse
import com.jujiiz.coins.features.coin.data.models.ResponseCoinDetailRaw
import com.jujiiz.coins.features.coin.data.models.ResponseCoinListRaw
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("v2/coins")
    suspend fun getCoinList(
        @Query("search") search: String?,
        @Query("offset") offset: Int?,
        @Query("limit") limit: Int?,
    ): BaseRemoteResponse<ResponseCoinListRaw>

    @GET("v2/coin/{uuid}")
    suspend fun getCoinDetail(
        @Path("uuid") id: String,
    ): BaseRemoteResponse<ResponseCoinDetailRaw>
}