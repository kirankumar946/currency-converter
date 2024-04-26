package com.kirankumarbathinoju.currency.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kirankumarbathinoju.currency.data.entities.CurrencyEntity
import com.kirankumarbathinoju.currency.data.entities.ExchangeRateEntity
import com.kirankumarbathinoju.currency.data.dao.CurrencyDao
import com.kirankumarbathinoju.currency.data.dao.ExchangeRateDao

/**
 * declaration of app database, using room database library
 */
@Database(entities = [ExchangeRateEntity::class, CurrencyEntity::class], version = 1)
abstract class CurrencyConverterDatabase : RoomDatabase() {

    abstract fun currencyDao(): CurrencyDao
    abstract fun exchangeRateDao(): ExchangeRateDao

    companion object {

        private const val DB_NAME = "currency-db"

        // For Singleton instantiation
        @Volatile
        private var instance: CurrencyConverterDatabase? = null

        fun getInstance(context: Context): CurrencyConverterDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): CurrencyConverterDatabase {
            return Room.databaseBuilder(
                context,
                CurrencyConverterDatabase::class.java, DB_NAME
            ).build()
        }
    }

}