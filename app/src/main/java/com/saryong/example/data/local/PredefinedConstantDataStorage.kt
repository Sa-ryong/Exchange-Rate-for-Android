package com.saryong.example.data.local

import android.content.Context
import com.saryong.example.util.fastLazy
import javax.inject.Inject

class PredefinedConstantDataStorage @Inject constructor(
  private val context: Context
) : PredefinedConstantStorage {

  override val currencies: List<CurrencySetting> by fastLazy { loadCurrencies() }

  private fun loadCurrencies() = context.loadCurrencies(CURRENCIES_FILENAME)

  companion object {
    const val CURRENCIES_FILENAME = "availability.json"
  }
}

inline fun <reified E : Any> Context.loadJsonList(filename: String) =
  LocalTextFileLoader.loadJsonList<E>(this, filename)

fun Context.loadCurrencies(filename: String) =
  LocalTextFileLoader.loadJsonList<CurrencySetting>(this, filename)