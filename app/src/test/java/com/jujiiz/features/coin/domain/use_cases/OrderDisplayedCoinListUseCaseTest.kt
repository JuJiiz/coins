package com.jujiiz.features.coin.domain.use_cases

import com.jujiiz.coins.features.coin.domain.models.CoinEntity
import com.jujiiz.coins.features.coin.domain.models.DisplayedCoinFeedEntity
import com.jujiiz.coins.features.coin.domain.models.DisplayedCoinListEntity
import com.jujiiz.coins.features.coin.domain.use_cases.OrderDisplayedCoinListUseCase
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class OrderDisplayedCoinListUseCaseTest {

    private val useCase: OrderDisplayedCoinListUseCase = OrderDisplayedCoinListUseCase()

    private fun generateCoinList(expectedItemCount: Int): List<CoinEntity> {
        return List(expectedItemCount) {
            CoinEntity(
                id = it.toString(),
                fullName = "Coin $it",
                shortName = "C$it",
                price = 5000.0,
                change = 0.5,
                imageUrl = null,
            )
        }
    }

    @Test
    fun orderDisplayedCoinList_FirstInitial_Loading() {
        val result = useCase.apply(
            coins = null,
            isSearching = false,
            isContainError = false,
            isLast = false,
        )
        assertTrue(result is DisplayedCoinFeedEntity.HasContent)
        result as DisplayedCoinFeedEntity.HasContent
        assertEquals(3, result.items.size)
        assertTrue(result.items.getOrNull(0) is DisplayedCoinListEntity.JustSpace)
        assertTrue(result.items.getOrNull(1) is DisplayedCoinListEntity.CoinTitle)
        assertTrue(result.items.getOrNull(2) is DisplayedCoinListEntity.Loading)
    }

    @Test
    fun orderDisplayedCoinList_FirstInitial_Error() {
        val result = useCase.apply(
            coins = null,
            isSearching = false,
            isContainError = true,
            isLast = false,
        )
        assertTrue(result is DisplayedCoinFeedEntity.HasContent)
        result as DisplayedCoinFeedEntity.HasContent
        assertEquals(3, result.items.size)
        assertTrue(result.items.getOrNull(0) is DisplayedCoinListEntity.JustSpace)
        assertTrue(result.items.getOrNull(1) is DisplayedCoinListEntity.CoinTitle)
        assertTrue(result.items.getOrNull(2) is DisplayedCoinListEntity.Error)
    }

    @Test
    fun orderDisplayedCoinList_NoSearching_TwentyItems() {
        val expectedItemCount = 20
        val coins = generateCoinList(expectedItemCount)
        val result = useCase.apply(
            coins = coins,
            isSearching = false,
            isContainError = false,
            isLast = false,
        )

        assertTrue(result is DisplayedCoinFeedEntity.HasContent)
        result as DisplayedCoinFeedEntity.HasContent
        assertTrue(result.items.getOrNull(0) is DisplayedCoinListEntity.JustSpace)
        assertTrue(result.items.getOrNull(1) is DisplayedCoinListEntity.TopThreeCoin)
        assertTrue(result.items.getOrNull(2) is DisplayedCoinListEntity.CoinTitle)

        assertEquals(
            expectedItemCount - 3,
            result.items.count { it is DisplayedCoinListEntity.CoinItem },
        )
    }

    @Test
    fun orderDisplayedCoinList_NoSearching_SixtyItems_ContainFiveInviteFriendInCorrectlyPosition() {
        val expectedItemCount = 60
        val coins = generateCoinList(expectedItemCount)
        val result = useCase.apply(
            coins = coins,
            isSearching = false,
            isContainError = false,
            isLast = false,
        )

        assertTrue(result is DisplayedCoinFeedEntity.HasContent)
        result as DisplayedCoinFeedEntity.HasContent

        assertTrue(result.items.getOrNull(0) is DisplayedCoinListEntity.JustSpace)
        assertTrue(result.items.getOrNull(1) is DisplayedCoinListEntity.TopThreeCoin)
        assertTrue(result.items.getOrNull(2) is DisplayedCoinListEntity.CoinTitle)

        assertEquals(
            DisplayedCoinListEntity.InviteFriend::class.java,
            result.items.getOrNull(5 + 3 + 0)?.javaClass,
        )
        assertEquals(
            DisplayedCoinListEntity.InviteFriend::class.java,
            result.items.getOrNull(10 + 3 + 1)?.javaClass,
        )
        assertEquals(
            DisplayedCoinListEntity.InviteFriend::class.java,
            result.items.getOrNull(20 + 3 + 2)?.javaClass,
        )
        assertEquals(
            DisplayedCoinListEntity.InviteFriend::class.java,
            result.items.getOrNull(40 + 3 + 3)?.javaClass,
        )
    }

    @Test
    fun orderDisplayedCoinList_Searching_TwentyItems() {
        val expectedItemCount = 20
        val coins = generateCoinList(expectedItemCount)
        val result = useCase.apply(
            coins = coins,
            isSearching = true,
            isContainError = false,
            isLast = false,
        )

        assertTrue(result is DisplayedCoinFeedEntity.HasContent)
        result as DisplayedCoinFeedEntity.HasContent

        assertTrue(result.items.getOrNull(0) is DisplayedCoinListEntity.JustSpace)
        assertTrue(result.items.getOrNull(1) is DisplayedCoinListEntity.CoinTitle)
        assertEquals(
            expectedItemCount,
            result.items.count { it is DisplayedCoinListEntity.CoinItem },
        )
    }

    @Test
    fun orderDisplayedCoinList_Searching_NoItems() {
        val expectedItemCount = 0
        val coins = generateCoinList(expectedItemCount)
        val result = useCase.apply(
            coins = coins,
            isSearching = true,
            isContainError = false,
            isLast = false,
        )

        assertTrue(result is DisplayedCoinFeedEntity.EmptyContent)
    }
}