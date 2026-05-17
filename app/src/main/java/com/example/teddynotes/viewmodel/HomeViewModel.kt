package com.example.teddynotes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teddynotes.repository.QuoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val repository = QuoteRepository()
    private val _quote = MutableStateFlow("Loading a thought for you...<3")

    val quote: StateFlow<String> = _quote

    init {
        viewModelScope.launch {
            _quote.value = repository.getQuote()
        }
    }
}