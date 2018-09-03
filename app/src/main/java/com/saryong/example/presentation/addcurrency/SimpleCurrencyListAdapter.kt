package com.saryong.example.presentation.addcurrency

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.saryong.example.presentation.addcurrency.SimpleCurrencyListAdapter.ViewHolder
import com.saryong.example.presentation.currencylist.item.CurrencyItem
import com.saryong.example.databinding.ItemSimpleCurrencyBinding

class SimpleCurrencyListAdapter : RecyclerView.Adapter<ViewHolder>() {
  var itemList = listOf<CurrencyItem>()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val binding = ItemSimpleCurrencyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return ViewHolder(binding)
  }

  override fun getItemCount(): Int = itemList.size

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.binding.currency = itemList[position]
  }

  class ViewHolder(val binding: ItemSimpleCurrencyBinding) : RecyclerView.ViewHolder(binding.root)
}