package com.kirankumarbathinoju.currency.data.repositories

import com.kirankumarbathinoju.currency.data.entities.CurrencyEntity
import com.kirankumarbathinoju.currency.data.entities.ExchangeRateEntity
import com.kirankumarbathinoju.currency.data.dao.CurrencyDao
import com.kirankumarbathinoju.currency.data.dao.ExchangeRateDao
import javax.inject.Inject
import javax.inject.Singleton

/**
 * repository to handle local data
 */
@Singleton
class LocalCurrencyRepository @Inject constructor(
    private val exchangeRateDao: ExchangeRateDao,
    private val currencyDao: CurrencyDao,
) {


    fun getCurrencies(): List<CurrencyEntity> {
        return currencyDao.getAll()
    }

    fun getRatesWithTimeGreaterThan(minTime: Long): List<ExchangeRateEntity> {
        return exchangeRateDao.getAllGreaterThanTimestamp(minTime)
    }

    fun saveCurrencies(currencies: List<CurrencyEntity>) {
        currencyDao.insertAll(currencies)
    }

    fun saveExchangeRates(exchangeRateList: List<ExchangeRateEntity>) {
        exchangeRateDao.insertAll(exchangeRateList)
    }

}