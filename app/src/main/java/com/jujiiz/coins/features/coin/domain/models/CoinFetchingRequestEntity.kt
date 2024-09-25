package com.jujiiz.coins.features.coin.domain.models

data class CoinFetchingRequestEntity(
    val timestamp:Long,
    val isPeriodicFetch:Boolean,
)