package com.jujiiz.coins.features.coin.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jujiiz.coins.core.COIN_LIST_ITEM_PER_PAGE
import com.jujiiz.coins.features.coin.domain.models.CoinDetailEntity
import com.jujiiz.coins.features.coin.domain.models.CoinEntity
import com.jujiiz.coins.features.coin.domain.models.CoinFetchingRequestEntity
import com.jujiiz.coins.features.coin.domain.models.DisplayedCoinFeedEntity
import com.jujiiz.coins.features.coin.domain.models.GetCoinListEntity
import com.jujiiz.coins.features.coin.domain.use_cases.GetCoinDetailUseCase
import com.jujiiz.coins.features.coin.domain.use_cases.GetCoinListUseCase
import com.jujiiz.coins.features.coin.domain.use_cases.OrderDisplayedCoinListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getCoinListUseCase: GetCoinListUseCase,
    private val orderDisplayedCoinListUseCase: OrderDisplayedCoinListUseCase,
    private val getCoinDetailUseCase: GetCoinDetailUseCase,
) : ViewModel() {
    private val _flowCoinRequest: MutableStateFlow<CoinFetchingRequestEntity> =
        MutableStateFlow(CoinFetchingRequestEntity(0, false))
    private val _flowSearchingText: MutableStateFlow<String> = MutableStateFlow("")
    private val _flowCurrentOffset: MutableStateFlow<Int> = MutableStateFlow(0)

    @OptIn(FlowPreview::class)
    private val flowGetCoinListInput: Flow<GetCoinListEntity> =
        combine(
            _flowCoinRequest,
            _flowSearchingText,
            _flowCurrentOffset
        ) { request, query, currentOffset ->
            GetCoinListEntity(
                query = query.takeIf(String::isNotBlank),
                nextIndex = currentOffset,
                perPage = if (request.isPeriodicFetch) currentOffset else COIN_LIST_ITEM_PER_PAGE,
                isPeriodicFetch = request.isPeriodicFetch
            )
        }.debounce(500)

    private val flowCoinList: MutableStateFlow<List<CoinEntity>?> = MutableStateFlow(null)
    private val flowIsError: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val flowIsLast: MutableStateFlow<Boolean> = MutableStateFlow(false)

    val flowDisplayedCoinList: Flow<DisplayedCoinFeedEntity> =
        combine(
            flowCoinList,
            _flowSearchingText,
            flowIsError,
            flowIsLast
        ) { coins, query, isContainError, isLast ->
            orderDisplayedCoinListUseCase.apply(
                coins = coins,
                isSearching = query.isNotBlank(),
                isContainError = isContainError,
                isLast = isLast,
            )
        }

    private val _stateLoading = mutableStateOf(false)
    val stateLoading: State<Boolean> = _stateLoading

    val flowSearchingText: Flow<String> = _flowSearchingText

    private val _stateCoinDetail: MutableState<CoinDetailEntity?> = mutableStateOf(null)
    val stateCoinDetail: State<CoinDetailEntity?> = _stateCoinDetail

    private var _fetchPeriodicJob: Job? = null

    init {
        collectInputAndFetchCoinList()
    }

    private fun collectInputAndFetchCoinList() {
        viewModelScope.launch(Dispatchers.IO) {
            flowGetCoinListInput.collect { input ->
                try {
                    val nextCoinList = getCoinListUseCase.apply(input)

                    println("[MLOG] nextCoinList size : ${nextCoinList.size}")

                    val current = flowCoinList.value ?: emptyList()
                    flowCoinList.value = if (input.nextIndex > 1 && !input.isPeriodicFetch) {
                        current + nextCoinList
                    } else {
                        nextCoinList
                    }

                    flowIsLast.value = nextCoinList.size < COIN_LIST_ITEM_PER_PAGE
                    flowIsError.value = false
                } catch (e: Exception) {
                    println("[ERROR]: ${e.message}")
                    flowIsError.value = true
                } finally {
                    _stateLoading.value = false
                    _fetchPeriodicJob = get10MinuteFetchingPeriodicJob()
                }
            }
        }
    }

    fun tryToFetchAgain() {
        viewModelScope.launch {
            flowIsError.value = false

            // Trigger fetching input component
            val currentPage = _flowCurrentOffset.value
            _flowCurrentOffset.value = currentPage
        }
    }

    fun updateSearchingText(text: String) {
        viewModelScope.launch {
            _flowSearchingText.value = text
            _flowCurrentOffset.value = 0
        }
    }

    fun requestNextPage() {
        viewModelScope.launch {
            val current = _flowCurrentOffset.value
            _flowCurrentOffset.value = current + COIN_LIST_ITEM_PER_PAGE
        }
    }

    fun refreshList(shouldBeSameSize: Boolean = false) {
        cancel10MinuteFetchingPeriodicJob()

        viewModelScope.launch {
            if (!shouldBeSameSize) {
                _stateLoading.value = true
                _flowCurrentOffset.value = 0
            }
            _flowCoinRequest.value = CoinFetchingRequestEntity(
                timestamp = System.currentTimeMillis(),
                isPeriodicFetch = shouldBeSameSize,
            )
        }
    }

    fun clearSearchText() {
        viewModelScope.launch {
            _flowSearchingText.value = ""
        }
    }

    fun fetchCoinDetail(id: String) {
        viewModelScope.launch {
            try {
                val detail = getCoinDetailUseCase.apply(id)
                _stateCoinDetail.value = detail
            } catch (_: Exception) {

            }
        }
    }

    fun clearCoinDetail() {
        viewModelScope.launch {
            _stateCoinDetail.value = null
        }
    }

    private fun get10MinuteFetchingPeriodicJob(): Job {
        return viewModelScope.launch {
            delay(1000 * 60 * 10)
            refreshList(shouldBeSameSize = true)
        }
    }

    private fun cancel10MinuteFetchingPeriodicJob() {
        _fetchPeriodicJob?.apply { if (!isCancelled) _fetchPeriodicJob?.cancel() }
    }
}