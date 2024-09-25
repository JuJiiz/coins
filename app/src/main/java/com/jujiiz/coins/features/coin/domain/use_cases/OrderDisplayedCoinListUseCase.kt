package com.jujiiz.coins.features.coin.domain.use_cases

import com.jujiiz.coins.features.coin.domain.models.CoinEntity
import com.jujiiz.coins.features.coin.domain.models.DisplayedCoinFeedEntity
import com.jujiiz.coins.features.coin.domain.models.DisplayedCoinListEntity
import javax.inject.Inject

class OrderDisplayedCoinListUseCase @Inject constructor() {
    fun apply(
        coins: List<CoinEntity>?,
        isSearching: Boolean,
        isContainError: Boolean,
        isLast: Boolean,
    ): DisplayedCoinFeedEntity {
        val feedItems = when (coins) {
            null -> when (isContainError) {
                true -> listOf(
                    DisplayedCoinListEntity.JustSpace(measureInDp = 16),
                    DisplayedCoinListEntity.CoinTitle,
                    DisplayedCoinListEntity.Error,
                )

                false -> listOf(
                    DisplayedCoinListEntity.JustSpace(measureInDp = 16),
                    DisplayedCoinListEntity.CoinTitle,
                    DisplayedCoinListEntity.Loading,
                )
            }

            else -> when (coins.isEmpty()) {
                true -> emptyList()

                false -> buildList {
                    add(DisplayedCoinListEntity.JustSpace(measureInDp = 16))

                    var theRest: List<CoinEntity> = coins
                    if (!isSearching) {
                        val first = coins.getOrNull(0)
                        val second = coins.getOrNull(1)
                        val third = coins.getOrNull(2)

                        if (first != null && second != null && third != null) {
                            add(
                                DisplayedCoinListEntity.TopThreeCoin(
                                    first = first,
                                    second = second,
                                    third = third,
                                )
                            )
                            theRest = try {
                                coins.subList(3, coins.size)
                            } catch (e: Exception) {
                                emptyList()
                            }
                        }
                    }

                    add(DisplayedCoinListEntity.CoinTitle)

                    var indexThatShouldPutInviteFriend = 5
                    val coinItemsWithInviteFriend: MutableList<DisplayedCoinListEntity> =
                        mutableListOf()
                    for (i in theRest.indices) {
                        val coin = theRest[i]
                        coinItemsWithInviteFriend.add(DisplayedCoinListEntity.CoinItem(coin))
                        if (i == indexThatShouldPutInviteFriend - 1) {
                            coinItemsWithInviteFriend.add(DisplayedCoinListEntity.InviteFriend)
                            indexThatShouldPutInviteFriend *= 2
                        }
                    }
                    addAll(coinItemsWithInviteFriend)

                    if (isContainError) add(DisplayedCoinListEntity.Error)
                    else if (!isLast) add(DisplayedCoinListEntity.Loading)
                }
            }
        }

        return if (feedItems.isEmpty()) {
            DisplayedCoinFeedEntity.EmptyContent
        } else {
            DisplayedCoinFeedEntity.HasContent(feedItems)
        }
    }
}