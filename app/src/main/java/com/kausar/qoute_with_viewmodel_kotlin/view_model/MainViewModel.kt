package com.kausar.qoute_with_viewmodel_kotlin.view_model

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.kausar.qoute_with_viewmodel_kotlin.model.Quote

class MainViewModel(private val context: Context): ViewModel() {
    private var quoteList: Array<Quote> = emptyArray()
    private var index = 0

    init {
        quoteList = loadQuoteFromAssets()
    }

    private fun loadQuoteFromAssets(): Array<Quote> {
        val inputStream = context.assets.open("quotes.json")
        val size: Int = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()

        val json = String(buffer, Charsets.UTF_8)
        val gson = Gson()
        return gson.fromJson(json, Array<Quote>::class.java)
    }

    fun getQuote() = quoteList[index]

    fun nextQuote() = quoteList[++index % quoteList.size]

    fun previousQuote() = quoteList[(--index + quoteList.size) % quoteList.size]
}


//used for creating viewModel object with parameterized constructor
class MainViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(context) as T
    }
}