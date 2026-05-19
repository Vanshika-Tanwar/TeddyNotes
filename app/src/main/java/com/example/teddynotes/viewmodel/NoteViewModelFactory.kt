package com.example.teddynotes.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.analytics.FirebaseAnalytics

class NoteViewModelFactory(
    private val application: Application,
    private val analytics: FirebaseAnalytics
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass : Class<T>) : T {
        if(modelClass.isAssignableFrom(NoteViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return NoteViewModel(application, analytics) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}