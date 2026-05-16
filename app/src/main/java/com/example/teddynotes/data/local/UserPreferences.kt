package com.example.teddynotes.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore : DataStore<Preferences> by preferencesDataStore(name = "user_prefs")
class UserPreferences(private val context: Context) {
    companion object{
        val USERNAME = stringPreferencesKey("username")
        val DOB = stringPreferencesKey("dob")
        val EMAIL = stringPreferencesKey("email")
        val GENDER = stringPreferencesKey("gender")
        val IS_ONBOARDED = booleanPreferencesKey("is_onboarded")

    }

    val username : Flow<String> = context.dataStore.data
        .map { it[USERNAME] ?: "" }
    val dob: Flow<String> = context.dataStore.data.map { it[DOB] ?: "" }
    val email: Flow<String> = context.dataStore.data.map { it[EMAIL] ?: "" }
    val gender: Flow<String> = context.dataStore.data.map { it[GENDER] ?: "" }
    val isOnboarded : Flow<Boolean> = context.dataStore.data
        .map { it[IS_ONBOARDED] ?: false }

    suspend fun saveUser(username: String, dob:String, email:String, gender:String){
        context.dataStore.edit{
            prefs ->
            prefs[USERNAME] = username
            prefs[DOB] = dob
            prefs[EMAIL] = email
            prefs[GENDER] = gender
            prefs[IS_ONBOARDED] = true
        }
    }
}