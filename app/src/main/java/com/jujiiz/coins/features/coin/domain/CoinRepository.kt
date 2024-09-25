package com.jujiiz.coins.features.coin.domain

import com.jujiiz.coins.features.coin.domain.models.CoinDetailEntity
import com.jujiiz.coins.features.coin.domain.models.CoinEntity
import com.jujiiz.coins.features.coin.domain.models.GetCoinDetailEntity
import com.jujiiz.coins.features.coin.domain.models.GetCoinListEntity

interface CoinRepository {
    suspend fun requestCoinList(input: GetCoinListEntity): List<CoinEntity>

    suspend fun requestCoinDetail(input: GetCoinDetailEntity): CoinDetailEntity
}