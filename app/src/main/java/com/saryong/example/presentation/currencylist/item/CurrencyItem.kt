package com.saryong.example.presentation.currencylist.item

import com.saryong.example.data.model.CurrencyModel
import org.threeten.bp.LocalDateTime

enum class UpdateStatus(val rawValue: Int) {
  LOADING(0),
  SUCCESS(1),
  FAILED(-1);
  
  companion object {
    fun valueOf(value: Int) =
      when (value) {
        0 -> LOADING
        1 -> SUCCESS
        else -> FAILED
      }
  }
}

data class CurrencyItem(
  val code: String,
  val name: String,
  val symbol: String,
  val exchangeRate: Double = 0.0,
  val updatedAt: String = "",
  val status: UpdateStatus = UpdateStatus.LOADING
) {
  val imagePath: String
    get() = code.toLowerCase() + ".png"
  
  val longName: String
    get() = "$name ($code)"
  
  val exchangeRateString: String
    get() = exchangeRate.toString()
  
  
}
