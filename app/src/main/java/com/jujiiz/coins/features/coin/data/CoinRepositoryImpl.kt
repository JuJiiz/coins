package com.jujiiz.coins.features.coin.data

import com.jujiiz.coins.core.di.ApiService
import com.jujiiz.coins.features.coin.data.models.RawToCoinDetailEntityMapper
import com.jujiiz.coins.features.coin.data.models.RawToCoinItemEntityMapper
import com.jujiiz.coins.features.coin.domain.CoinRepository
import com.jujiiz.coins.features.coin.domain.models.CoinDetailEntity
import com.jujiiz.coins.features.coin.domain.models.CoinEntity
import com.jujiiz.coins.features.coin.domain.models.GetCoinDetailEntity
import com.jujiiz.coins.features.coin.domain.models.GetCoinListEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CoinRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val rawToCoinItemEntityMapper: RawToCoinItemEntityMapper,
    private val rawToCoinDetailEntityMapper: RawToCoinDetailEntityMapper,
) : CoinRepository {

    override suspend fun requestCoinList(input: GetCoinListEntity): List<CoinEntity> {
        val response = apiService.getCoinList(
            search = input.query,
            offset = input.nextIndex,
            limit = input.perPage,
        )

        if (response.status != "success") throw Exception("Request is not success")
        val data = response.data ?: throw Exception("Request is not contain data")

        val coins = data.coins ?: emptyList()

        return coins.map(rawToCoinItemEntityMapper::apply)
    }

    override suspend fun requestCoinDetail(input: GetCoinDetailEntity): CoinDetailEntity {
        val response = apiService.getCoinDetail(
            id = input.id,
        )

        if (response.status != "success") throw Exception("Request is not success")
        val data = response.data ?: throw Exception("Request is not contain data")
        val coin = data.coin ?: throw Exception("Invalid coin data")

        return rawToCoinDetailEntityMapper.apply(coin)
    }

}