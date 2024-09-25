package com.jujiiz.coins.features.coin.domain.models

sealed class DisplayedCoinFeedEntity {

    companion object {
        fun getDefault(): DisplayedCoinFeedEntity = HasContent(emptyList())
    }

    data object EmptyContent : DisplayedCoinFeedEntity()

    data class HasContent(
        val items: List<DisplayedCoinListEntity>
    ) : DisplayedCoinFeedEntity()
}