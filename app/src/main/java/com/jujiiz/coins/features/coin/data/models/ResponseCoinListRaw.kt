package com.jujiiz.coins.features.coin.data.models

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ResponseCoinListRaw(
    val stats: CoinStatsRaw?,
    val coins: List<CoinItemRaw>?,
)

@Keep
data class CoinStatsRaw(
    @SerializedName("total") val total: Int?,
    @SerializedName("totalCoins") val totalCoins: Int?,
    @SerializedName("totalExchanges") val totalExchanges: Int?,
    @SerializedName("totalMarketCap") val totalMarketCap: String?,
    @SerializedName("total24hVolume") val total24hVolume: String?,
)

@Keep
data class CoinItemRaw(
    val uuid: String?,
    val symbol: String?,
    val name: String?,
    val color: String?,
    val iconUrl: String?,
    val marketCap: String?,
    val price: String?,
    val listedAt: Int?,
    val tier: Int?,
    val change: String?,
    val rank: Int?,
    val sparkline: List<String>,
    val lowVolume: Boolean?,
    @SerializedName("coinrankingUrl") val coinRankingUrl: String?,
    val the24hVolume: String?,
    val btcPrice: String?,
    val contractAddresses: List<String>?,
)