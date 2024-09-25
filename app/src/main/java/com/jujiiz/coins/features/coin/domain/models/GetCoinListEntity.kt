package com.jujiiz.coins.features.coin.domain.models

data class GetCoinListEntity(
    val query: String?,
    val nextIndex: Int,
    val perPage: Int,
    val isPeriodicFetch:Boolean,
)