package com.kirankumarbathinoju.currency.data.dao

import androidx.room.*
import com.kirankumarbathinoju.currency.data.entities.ExchangeRateEntity

/**
 * data access object for exchange rate currency
 */
@Dao
interface ExchangeRateDao {

    @Query("SELECT * FROM exchange_rate order by symbol asc")
    fun getAll(): List<ExchangeRateEntity>

    @Query("SELECT * FROM exchange_rate where timestamp > :minTime order by symbol asc")
    fun getAllGreaterThanTimestamp(minTime: Long): List<ExchangeRateEntity>

    @Query("SELECT * FROM exchange_rate WHERE symbol = :value LIMIT 1")
    fun findBySymbol(value: String): ExchangeRateEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(exchangeRate: ExchangeRateEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(currencies: Collection<ExchangeRateEntity>)

    @Delete
    fun delete(exchangeRate: ExchangeRateEntity)

}