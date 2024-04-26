package com.kirankumarbathinoju.currency.data.repositories

import android.util.Log
import com.kirankumarbathinoju.currency.data.dto.ExchangeRateDTO
import com.kirankumarbathinoju.currency.data.exceptions.GenericException
import com.kirankumarbathinoju.currency.data.exceptions.NetworkException
import com.kirankumarbathinoju.currency.data.repositories.api.ApiServiceInterface
import javax.inject.Inject
import javax.inject.Singleton

/**
 * repository to handle external network call
 */
@Singleton
class HttpCurrencyRepository @Inject constructor() {

    private val tag = "httpRepository"

    fun fetchAllCurrencies(): Map<String, String> {

        Log.d(tag, "fetching currencies from api...")

        val apiInterface = ApiServiceInterface.create()
        val request =
            apiInterface.getCurrencies()

        val response = request.execute()

        if (!response.isSuccessful) {
            Log.w(
                tag,
                "a network error occurred during comunication, throwing a NetworkException ${response.errorBody()}"
            )
            throw NetworkException()
        }

        val responseBody = response.body()
        if (responseBody == null) {
            Log.w(
                tag,
                "an unexpected error occurred during comunication, throwing a GenericException ${response.errorBody()}"
            )
            throw GenericException()
        }

        Log.d(tag, "call executhed, response body: $responseBody")

        return responseBody
    }


    fun fetchLatestExchangeRates(apiKey: String): ExchangeRateDTO {

        Log.d(tag, "fetching currencies from api...")

        val apiInterface = ApiServiceInterface.create()
        val request =
            apiInterface.getLatestExchangeRate(apiKey)

        val response = request.execute()
        Log.i(tag, "call executhed, status code is ${response.code()}")

        if (!response.isSuccessful) {
            Log.w(
                tag,
                "a network error occurred during comunication, throwing a NetworkException ${response.errorBody()}"
            )
            throw NetworkException()
        }

        val rates = response.body()
        if (rates == null) {
            Log.w(
                tag,
                "an unexpected error occurred during comunication, throwing a GenericException ${response.errorBody()}"
            )
            throw GenericException()
        }
        Log.d(tag, "call executhed, response body: $rates")

        return rates
    }

}