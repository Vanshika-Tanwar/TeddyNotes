package com.example.teddynotes.model

data class Note(
    val id: Int = 0,
    val title: String,
    val date: String,
    val content: String,
    val mood: Mood
)