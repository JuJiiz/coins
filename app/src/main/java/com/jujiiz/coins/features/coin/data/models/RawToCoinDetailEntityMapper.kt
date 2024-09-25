package com.jujiiz.coins.features.coin.data.models

import android.icu.text.DecimalFormat
import androidx.core.graphics.toColorInt
import com.jujiiz.coins.features.coin.domain.models.CoinDetailEntity
import java.util.function.Function
import javax.inject.Inject

class RawToCoinDetailEntityMapper @Inject constructor() :
    Function<CoinDetailRaw, CoinDetailEntity> {

    override fun apply(raw: CoinDetailRaw): CoinDetailEntity {

        val colorHex = raw.color
            ?.let {
                try {
                    it.toColorInt()
                } catch (e: Exception) {
                    null
                }
            }

        val marketCapRaw = raw.marketCap ?: throw Exception("Coin {marketCap} is null")
        val marketCapLabel = when {
            marketCapRaw.length >= 10 -> {
                val marketCapInDouble = marketCapRaw.toDouble()
                val decimalFormat = DecimalFormat("$ #,##0.00")
                "${decimalFormat.format(marketCapInDouble / 1000000000)} trillion"
            }

            marketCapRaw.length == 9 -> {
                val marketCapInDouble = marketCapRaw.toDouble()
                val decimalFormat = DecimalFormat("$ #,##0.00")
                "${decimalFormat.format(marketCapInDouble / 100000000)} billion"
            }

            marketCapRaw.length >= 7 -> {
                val marketCapInDouble = marketCapRaw.toDouble()
                val decimalFormat = DecimalFormat("$ #,##0.00")
                "${decimalFormat.format(marketCapInDouble / 1000000)} million"
            }

            else -> {
                val marketCapInInt = marketCapRaw.toInt()
                DecimalFormat("$ #,###,##0").format(marketCapInInt)
            }
        }

        val description = raw.description
        val websiteUrl = raw.websiteUrl ?: throw Exception("Coin {websiteUrl} is null")

        return CoinDetailEntity(
            colorInt = colorHex,
            marketCapLabel = marketCapLabel,
            description = description,
            websiteUrl = websiteUrl,
        )
    }
}