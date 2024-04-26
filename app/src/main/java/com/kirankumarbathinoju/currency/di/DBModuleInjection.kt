package com.kirankumarbathinoju.currency.di

import android.content.Context
import com.kirankumarbathinoju.currency.data.database.CurrencyConverterDatabase
import com.kirankumarbathinoju.currency.data.dao.CurrencyDao
import com.kirankumarbathinoju.currency.data.dao.ExchangeRateDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * this class describes how hilt injects specific objects
 */
@InstallIn(SingletonComponent::class)
@Module
class DBModuleInjection {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): CurrencyConverterDatabase {
        return CurrencyConverterDatabase.getInstance(context)
    }


    @Provides
    fun provideCurrencyDao(appDatabase: CurrencyConverterDatabase): CurrencyDao {
        return appDatabase.currencyDao()
    }


    @Provides
    fun provideExchangeRateDao(appDatabase: CurrencyConverterDatabase): ExchangeRateDao {
        return appDatabase.exchangeRateDao()
    }
}