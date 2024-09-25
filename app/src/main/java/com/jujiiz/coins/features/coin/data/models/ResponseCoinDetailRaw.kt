package com.jujiiz.coins.features.coin.data.models

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ResponseCoinDetailRaw(
    val coin: CoinDetailRaw?,
)

@Keep
data class CoinDetailRaw(
    val uuid: String?,
    val symbol: String?,
    val name: String?,
    val description: String?,
    val color: String?,
    val iconUrl: String?,
    val websiteUrl: String?,
    val links: List<Link>?,
    val supply: Supply?,
    val the24hVolume: String?,
    val marketCap: String?,
    val fullyDilutedMarketCap: String?,
    val price: String?,
    val btcPrice: String?,
    val priceAt: Int?,
    val change: String?,
    val rank: Int?,
    val numberOfMarkets: Int?,
    val numberOfExchanges: Int?,
    val sparkline: List<String>?,
    val allTimeHigh: AllTimeHigh?,
    @SerializedName("coinrankingUrl") val coinRankingUrl: String?,
    val lowVolume: Boolean?,
    val listedAt: Int?,
    val notices: List<Notice>?,
    val contractAddresses: List<String>?,
    val tags: List<String>?,
)

@Keep
data class AllTimeHigh(
    val price: String?,
    val timestamp: Int?,
)

@Keep
data class Link(
    val name: String?,
    val url: String?,
    val type: String?,
)

@Keep
data class Notice(
    val type: String?,
    val value: String?,
)

@Keep
data class Supply(
    val confirmed: Boolean?,
    val supplyAt: Int?,
    val circulating: String?,
    val total: String?,
    val max: String?,
)