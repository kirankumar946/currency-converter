package com.kirankumarbathinoju.currency.data.repositories

import com.kirankumarbathinoju.currency.data.entities.Currency
import com.kirankumarbathinoju.currency.data.entities.ExchangeRate
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


    fun getCurrencies(): List<Currency> {
        return currencyDao.getAll()
    }

    fun getRatesWithTimeGreaterThan(minTime: Long): List<ExchangeRate> {
        return exchangeRateDao.getAllGreaterThanTimestamp(minTime)
    }

    fun saveCurrencies(currencies: List<Currency>) {
        currencyDao.insertAll(currencies)
    }

    fun saveExchangeRates(exchangeRateList: List<ExchangeRate>) {
        exchangeRateDao.insertAll(exchangeRateList)
    }

}