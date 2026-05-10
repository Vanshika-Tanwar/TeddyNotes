package com.example.teddynotes.data

import androidx.room.TypeConverter
import com.example.teddynotes.model.Mood

class MoodConverter {
    @TypeConverter
    fun fromMood(mood: Mood) : String = mood.name

    @TypeConverter
    fun toMood(mood: String) : Mood = Mood.valueOf(mood)
}