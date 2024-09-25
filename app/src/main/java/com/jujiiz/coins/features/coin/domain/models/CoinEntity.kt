package com.jujiiz.coins.features.coin.domain.models

data class CoinEntity(
    val id: String,
    val fullName: String,
    val shortName: String,
    val price: Double,
    val change: Double,
    val imageUrl: String?,
)