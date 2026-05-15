package com.example.teddynotes.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private fun buildRetrofit(baseUrl : String) : Retrofit{
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val quoteAPI : QuoteAPI by lazy{
        buildRetrofit("https://zenquotes.io/api/")
            .create(QuoteAPI::class.java)
    }

    val chatApi: ChatBotAPI by lazy {
        buildRetrofit("https://generativelanguage.googleapis.com/")
            .create(ChatBotAPI::class.java)
    }
}