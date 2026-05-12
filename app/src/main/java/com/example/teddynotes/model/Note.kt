package com.example.teddynotes.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "notes")
data class Note(
    val title: String,
    @PrimaryKey
    val date: String,
    val content: String,
    val mood: Mood
)