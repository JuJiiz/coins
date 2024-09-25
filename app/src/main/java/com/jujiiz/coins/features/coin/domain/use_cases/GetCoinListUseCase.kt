package com.jujiiz.coins.features.coin.domain.use_cases

import com.jujiiz.coins.features.coin.domain.CoinRepository
import com.jujiiz.coins.features.coin.domain.models.CoinEntity
import com.jujiiz.coins.features.coin.domain.models.GetCoinListEntity
import javax.inject.Inject

class GetCoinListUseCase @Inject constructor(
    private val coinRepository: CoinRepository,
) {
    suspend fun apply(
        input: GetCoinListEntity,
    ): List<CoinEntity> {
        return coinRepository.requestCoinList(input)
    }
}