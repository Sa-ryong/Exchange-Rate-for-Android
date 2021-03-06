package com.saryong.example.presentation.currencydetail

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.saryong.example.data.local.CurrencySetting
import com.saryong.example.data.model.CurrencyModel
import com.saryong.example.databinding.FragmentCurrencyDetailBinding
import com.saryong.example.util.extention.viewModelProvider
import com.saryong.example.util.fastLazy
import com.saryong.example.util.livedata.EventObserver
import dagger.android.support.DaggerFragment
import javax.inject.Inject
import android.view.inputmethod.InputMethodManager.HIDE_NOT_ALWAYS
import android.content.Context.INPUT_METHOD_SERVICE
import android.view.inputmethod.InputMethodManager


/**
 * A placeholder fragment containing a simple view.
 */
class CurrencyDetailFragment : DaggerFragment() {
  @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
  
  private val viewModel by fastLazy {
    viewModelProvider(viewModelFactory) as CurrencyDetailViewModel
  }
  
  private lateinit var binding: FragmentCurrencyDetailBinding
  
  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    binding = FragmentCurrencyDetailBinding.inflate(inflater, container, false)
    
    viewModel.initAction.observe(this, EventObserver {
      bindCurrencySettings(it)
    })
  
    viewModel.snackbarMessage.observe(this, Observer {
      it?.let { message ->
        if (view != null) {
          Snackbar.make(view!!, message, Snackbar.LENGTH_LONG).show()
        }
      }
    })
  
    if (savedInstanceState == null) {
      arguments?.getString(ARG_TARGET_CURRENCY)?.let {
        viewModel.init(it)
      }
      
      viewModel.targetCurrency.observe(this, Observer { currency ->
        currency?.let {
          binding.targetCurrencyText.text = it.longName
          binding.exchangeRateText.text = it.exchangeRate.toString()
          binding.updatedDatetimeText.text = it.updatedAt
        }
      })
    }
    
    binding.setLifecycleOwner(this)
    binding.viewModel = viewModel
    binding.containerLayout.setOnClickListener {
      hideKeyboard(it)
    }
    
    return binding.root
  }
  
  private fun bindCurrencySettings(source: CurrencySetting) {
    binding.sourceCurrencyText.text = longNameFor(source)
  }
  
  private fun longNameFor(currency: CurrencySetting) =
    "${currency.name} (${currency.code})"
  
  private fun hideKeyboard(view: View) {
    val manager = context?.applicationContext?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    manager?.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
  }
  
  companion object {
    private const val ARG_TARGET_CURRENCY = "arg.TARGET_CURRENCY"
    
    fun newInstance(targetCurrency: String): CurrencyDetailFragment =
      CurrencyDetailFragment().apply {
        arguments = bundleOf(ARG_TARGET_CURRENCY to targetCurrency)
      }
  }
}
