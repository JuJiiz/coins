package com.jujiiz.coins.features.coin.domain.models

sealed class DisplayedCoinListEntity(val isAbleToFullSpan:Boolean) {

    data object Loading : DisplayedCoinListEntity(true)

    data object Error : DisplayedCoinListEntity(true)

    data object CoinTitle : DisplayedCoinListEntity(true)

    data object InviteFriend : DisplayedCoinListEntity(false)

    data class TopThreeCoin(
        val first: CoinEntity,
        val second: CoinEntity,
        val third: CoinEntity,
    ) : DisplayedCoinListEntity(true)

    data class CoinItem(
        val entity: CoinEntity,
    ) : DisplayedCoinListEntity(false)

    data class JustSpace(
        val measureInDp:Int,
    ): DisplayedCoinListEntity(true)
}
