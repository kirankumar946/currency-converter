package com.kirankumarbathinoju.currency

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kirankumarbathinoju.currency.data.dao.CurrencyDao
import com.kirankumarbathinoju.currency.data.dao.ExchangeRateDao
import com.kirankumarbathinoju.currency.data.database.CurrencyConverterDatabase
import com.kirankumarbathinoju.currency.data.entities.CurrencyEntity
import com.kirankumarbathinoju.currency.data.entities.ExchangeRateEntity
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class DaoTest {

    private lateinit var currencyDao: CurrencyDao
    private lateinit var exchangeRateDao: ExchangeRateDao
    private lateinit var db: CurrencyConverterDatabase

    @Before
    fun setupDbAndDao() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, CurrencyConverterDatabase::class.java
        ).build()
        currencyDao = db.currencyDao()
        exchangeRateDao = db.exchangeRateDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun currencyDaoOperations() {

        //populating a list of currencies
        val usd = CurrencyEntity("USD", "Dollaro")
        val eur = CurrencyEntity("EUR", "Euro")
        val currencies = arrayListOf(usd, eur)

        // reading all currencies before insert, expecting an empty array
        var persistedCurrencies = currencyDao.getAll()
        assertTrue(persistedCurrencies.isEmpty())

        //saving currencies in db
        currencyDao.insertAll(currencies)

        // reading all currencies, expecting an array equals to currencies
        persistedCurrencies = currencyDao.getAll()
        assertTrue(persistedCurrencies.isNotEmpty())
        assertEquals(currencies.size, persistedCurrencies.size)

        //saving another currency, this will overwrite the existing value in db, size shouln't increase
        currencyDao.insert(CurrencyEntity("EUR", "Euro"))
        persistedCurrencies = currencyDao.getAll()
        assertTrue(persistedCurrencies.isNotEmpty())
        assertEquals(currencies.size, persistedCurrencies.size)

        //finding an element
        val eurCurrency = currencyDao.findBySymbol("EUR")
        assertNotNull(eurCurrency)
        //finding an invalid element
        val invalidCurrency = currencyDao.findBySymbol("XXX")
        assertNull(invalidCurrency)

        //deleting an element
        currencyDao.delete(eur)
        persistedCurrencies = currencyDao.getAll()
        assertTrue(persistedCurrencies.isNotEmpty())
        assertTrue(currencies.size > persistedCurrencies.size)
    }

    @Test
    fun exchangeRateDaoOperations() {

        //populating a list of currencies
        val rateUsd = ExchangeRateEntity("USD", 1.0, System.currentTimeMillis(), "USD")
        val rateEur = ExchangeRateEntity("EUR", 1.05, System.currentTimeMillis(), "USD")
        val rateJpy = ExchangeRateEntity("JPY", 144.0, System.currentTimeMillis(), "USD")
        val currencies = arrayListOf(rateUsd, rateEur, rateJpy)

        // reading all currencies before insert, expecting an empty array
        var persistedExchangeRates = exchangeRateDao.getAll()
        assertTrue(persistedExchangeRates.isEmpty())

        //saving currencies in db
        exchangeRateDao.insertAll(currencies)

        // reading all currencies, expecting an array equals to currencies
        persistedExchangeRates = exchangeRateDao.getAll()
        assertTrue(persistedExchangeRates.isNotEmpty())
        assertEquals(currencies.size, persistedExchangeRates.size)

        //saving another currency, this will overwrite the existing value in db, size shouln't increase
        exchangeRateDao.insert(ExchangeRateEntity("USD", 1.0, System.currentTimeMillis(), "USD"))
        persistedExchangeRates = exchangeRateDao.getAll()
        assertTrue(persistedExchangeRates.isNotEmpty())
        assertEquals(currencies.size, persistedExchangeRates.size)

        //finding an element
        val eur = exchangeRateDao.findBySymbol("EUR")
        assertNotNull(eur)
        //finding an invalid element
        val invalidRate = exchangeRateDao.findBySymbol("XXX")
        assertNull(invalidRate)

        //deleting an element
        exchangeRateDao.delete(rateEur)
        persistedExchangeRates = exchangeRateDao.getAll()
        assertTrue(persistedExchangeRates.isNotEmpty())
        assertTrue(currencies.size > persistedExchangeRates.size)
    }


}