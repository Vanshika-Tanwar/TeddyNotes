package com.example.teddynotes.model

import androidx.compose.ui.graphics.Color
import com.example.teddynotes.ui.theme.MoodAngry
import com.example.teddynotes.ui.theme.MoodAnxious
import com.example.teddynotes.ui.theme.MoodCalm
import com.example.teddynotes.ui.theme.MoodHappy
import com.example.teddynotes.ui.theme.MoodSad

enum class Mood(val emoji: String, val label : String, val color : Color) { //no data class as fixed set of 5 values
    HAPPY("😄", "Happy", MoodHappy),
    SAD("🙁","Sad", MoodSad),
    ANGRY("😠","Angry", MoodAngry),
    CALM("😌","Calm", MoodCalm),
    ANXIOUS("😨","Anxious", MoodAnxious)
}