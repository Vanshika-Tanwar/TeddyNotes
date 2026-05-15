package com.example.teddynotes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.teddynotes.repository.QuoteRepository

class HomeViewModelFactory(private  val repository : QuoteRepository) : ViewModelProvider.Factory {
    override fun <T: ViewModel> create(
        modelClass: Class<T>
    ):T{
        if(modelClass.isAssignableFrom(HomeViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown viewmodel class")
    }
}