package com.example.teddynotes.data.remote

import com.example.teddynotes.model.QuoteResponse
import retrofit2.http.GET

interface QuoteAPI{
    @GET("random")
    suspend fun getQuote(): List<QuoteResponse>
}