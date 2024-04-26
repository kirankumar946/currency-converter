package com.kirankumarbathinoju.currency.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kirankumarbathinoju.currency.data.CurrencyValue
import com.kirankumarbathinoju.currency.data.entities.CurrencyEntity
import com.kirankumarbathinoju.currency.usecases.CurrencyUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale
import javax.inject.Inject

/**
 * view model class used by [com.kirankumarbathinoju.currency.ui.CurrenciesListFragment]
 */
@HiltViewModel
class CurrenciesListViewModel @Inject internal constructor(private val useCase: CurrencyUseCases) :
    ViewModel() {

    //immutable to observe in the view
    val currencies: LiveData<State<List<CurrencyEntity>>> get() = _currencies
    private val _currencies = MutableLiveData<State<List<CurrencyEntity>>>()

    //immutable to observe in the view
    val currencyValues: LiveData<State<List<CurrencyValue>>> get() = _currencyValues
    private val _currencyValues = MutableLiveData<State<List<CurrencyValue>>>()

    //the current select currency
    val selectedCurrency: CurrencyEntity? get() = _selectedCurrency
    private var _selectedCurrency: CurrencyEntity? = null

    //the amount to convert in different currencies
    val amountToConvert: Double? get() = _amountToConvert
    private var _amountToConvert: Double? = null

    //at startup i'm setting all states to loading and empty in order to show desidered ui widgets
    //and loading all currencies list (from api or db)
    init {
        loadCurrencies()
    }

    fun loadCurrencies() {
        _currencies.value = State.loading()
        _currencyValues.value = State.empty()

        viewModelScope.launch {
            val state = withContext(Dispatchers.Default) {
                try {
                    val currenciesValue = useCase.getAllCurrencies()
                    State.success(currenciesValue)
                } catch (exception: Exception) {
                    State.error(exception.message)
                }
            }
            _currencies.value = state
        }

    }

    //this will be invoked when currency is selected from dropdown
    fun changeSelectedCurrency(selectedCurrency: CurrencyEntity) {
        _selectedCurrency = selectedCurrency
        loadExchangeRates()
    }

    //this will be invoked when amount is changed inside text edit
    fun changeAmount(amount: Double?) {
        amount?.let {
            _amountToConvert = it
            loadExchangeRates()
        }
    }

    /**
     * load currencies rate, with error handling, i'm using [State] to handle the loading, error, success states.
     * error case is handled using custom exceptions. Take a look at [com.kirankumarbathinoju.currency.data.exceptions.NetworkException]
     * and [com.kirankumarbathinoju.currency.data.exceptions.GenericException]
     */
    private fun loadExchangeRates() {
        if (_selectedCurrency != null && _amountToConvert != null) {
            _currencyValues.value = State.loading()

            viewModelScope.launch {
                val state = withContext(Dispatchers.Default) {
                    try {
                        val currenciesValue = useCase.getRatesByCurrencyAndAmount(
                            _selectedCurrency!!,
                            _amountToConvert!!
                        )
                        State.success(currenciesValue)
                    } catch (exception: Exception) {
                        State.error(exception.message)
                    }
                }
                _currencyValues.value = state
            }
        }
    }
}