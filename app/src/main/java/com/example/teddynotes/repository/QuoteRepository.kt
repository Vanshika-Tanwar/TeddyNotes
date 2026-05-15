package com.example.teddynotes.repository

import com.example.teddynotes.data.remote.RetrofitClient

class QuoteRepository {
    suspend fun getQuote() : String{
        return try{
            val response = RetrofitClient.quoteAPI.getQuote()
            response[0].q
        } catch (e: Exception){
            "The secret of getting ahead is getting started."
        }
    }
}