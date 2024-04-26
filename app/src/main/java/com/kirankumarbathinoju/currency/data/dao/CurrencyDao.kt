package com.kirankumarbathinoju.currency.data.dao

import androidx.room.*
import com.kirankumarbathinoju.currency.data.entities.CurrencyEntity

/**
 * data access object for currency entity
 */
@Dao
interface CurrencyDao {

    @Query("SELECT * FROM currency order by symbol asc")
    fun getAll(): List<CurrencyEntity>

    @Query("SELECT * FROM currency WHERE symbol = :value LIMIT 1")
    fun findBySymbol(value: String): CurrencyEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(Currency: CurrencyEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(currencies: Collection<CurrencyEntity>)

    @Delete
    fun delete(currency: CurrencyEntity)
}