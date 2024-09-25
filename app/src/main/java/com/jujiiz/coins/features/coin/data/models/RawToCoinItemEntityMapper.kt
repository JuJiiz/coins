package com.jujiiz.coins.features.coin.data.models

import com.jujiiz.coins.features.coin.domain.models.CoinEntity
import java.util.function.Function
import javax.inject.Inject

class RawToCoinItemEntityMapper @Inject constructor() : Function<CoinItemRaw, CoinEntity> {

    override fun apply(raw: CoinItemRaw): CoinEntity {
        val id = raw.uuid ?: throw Exception("Coin {uuid} is null")
        val fullName = raw.name ?: throw Exception("Coin {name} is null")
        val shortName = raw.symbol ?: throw Exception("Coin {symbol} is null")
        val price = raw.price?.toDouble() ?: throw Exception("Coin {price} is null")
        val change = raw.change?.toDouble() ?: throw Exception("Coin {change} is null")

        return CoinEntity(
            id = id,
            fullName = fullName,
            shortName = shortName,
            price = price,
            change = change,
            imageUrl = raw.iconUrl,
        )
    }
}