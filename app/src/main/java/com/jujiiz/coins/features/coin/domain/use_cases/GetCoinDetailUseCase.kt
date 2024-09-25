package com.jujiiz.coins.features.coin.domain.use_cases

import com.jujiiz.coins.features.coin.domain.CoinRepository
import com.jujiiz.coins.features.coin.domain.models.CoinDetailEntity
import com.jujiiz.coins.features.coin.domain.models.GetCoinDetailEntity
import javax.inject.Inject

class GetCoinDetailUseCase @Inject constructor(
    private val coinRepository: CoinRepository,
) {
    suspend fun apply(coinId: String): CoinDetailEntity {
        val input = GetCoinDetailEntity(id = coinId)
        return coinRepository.requestCoinDetail(input)
    }
}