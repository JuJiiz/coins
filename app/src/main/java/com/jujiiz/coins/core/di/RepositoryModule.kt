package com.jujiiz.coins.core.di

import com.jujiiz.coins.features.coin.data.CoinRepositoryImpl
import com.jujiiz.coins.features.coin.domain.CoinRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindCoinRepository(impl: CoinRepositoryImpl): CoinRepository
}