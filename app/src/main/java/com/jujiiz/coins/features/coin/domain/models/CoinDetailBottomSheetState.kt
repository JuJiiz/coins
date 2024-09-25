package com.jujiiz.coins.features.coin.domain.models

sealed class CoinDetailBottomSheetState {
    data object Hide: CoinDetailBottomSheetState()
    data class Show(val coin:CoinEntity) : CoinDetailBottomSheetState()
}