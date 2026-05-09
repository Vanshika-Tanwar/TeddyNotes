package com.example.teddynotes.model

enum class Mood(val emoji: String, val label : String) { //no data class as fixed set of 5 values
    HAPPY("😄", "Happy"),
    SAD("🙁","Sad"),
    ANGRY("😠","Angry"),
    CALM("😌","Calm"),
    ANXIOUS("😨","Anxious")
}