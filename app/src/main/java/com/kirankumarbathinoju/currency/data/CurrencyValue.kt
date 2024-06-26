package com.kirankumarbathinoju.currency.data

/**
 * model class to handle currencies value in UI, used mainly by adapter [com.kirankumarbathinoju.currency.ui.adapters.CurrenciesListAdapter]
 */
data class CurrencyValue(

    val symbol: String,

    val amount: Double,
)