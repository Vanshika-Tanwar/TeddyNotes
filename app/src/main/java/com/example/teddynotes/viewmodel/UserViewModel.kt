package com.example.teddynotes.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.teddynotes.data.local.UserPreferences
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {
    private val prefs = UserPreferences(application)

    val username: StateFlow<String> =
        prefs.username.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "")

    val dob: StateFlow<String> =
        prefs.dob.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "")
    val email: StateFlow<String> =
        prefs.email.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "")
    val gender: StateFlow<String> =
        prefs.gender.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "")
    val isOnboarded: StateFlow<Boolean> =
        prefs.isOnboarded.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    fun saveUser(username: String, dob:String, email:String, gender:String){
        viewModelScope.launch {
            prefs.saveUser(username,dob,email,gender)
        }
    }
}