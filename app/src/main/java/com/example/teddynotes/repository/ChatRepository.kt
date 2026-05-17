package com.example.teddynotes.repository

import android.util.Log
import com.example.teddynotes.model.Note
import com.google.firebase.Firebase
import com.google.firebase.ai.ai
import com.google.firebase.ai.type.GenerativeBackend
import com.google.firebase.ai.type.content

class ChatRepository {

    private val model = Firebase.ai(backend = GenerativeBackend.googleAI())
        .generativeModel("gemini-3-flash-preview")

    suspend fun getReply(message: String, notes: List<Note>, username: String, age: String, gender: String): String {

        val prompt = buildPrompt(message, notes, username, age, gender)

        return try {

            val response = model.generateContent(
                content { text(prompt) })
            response.text ?: "Teddy is thinking 🐻"

        } catch (e: Exception) {
            Log.e("GEMINI", "API FAILED", e)
            "Teddy had trouble connecting 🥲"
        }
    }

    private fun buildPrompt(
        message: String, notes: List<Note>, username: String, age: String, gender: String
    ): String {

        val notesContext = if (notes.isEmpty()) {
            "No previous notes available."
        } else {
            notes.takeLast(4).joinToString("\n") { note ->
                "Date: ${note.date}, Mood: ${note.mood}, Title: ${note.title}, Entry: ${note.content}"
            }
        }

        return """
    You are Teddy 🐻 — ${username}'s close friend, their Age: ${age} & Gender: ${gender}.
    You've read their journal quietly. You know their life. But you don't bring it up unless it's natural.
    
    Be like a friend texting. Not a therapist. Not a life coach.
    
    Rules:
    - If they say they're good, just be happy for them. Maybe ask what's up casually.
    - Don't analyze or explain their feelings back to them.
    - Don't reference their notes directly unless THEY bring it up first.
    - If they say "remember when I said..." or reference a past note, engage with it naturally.
    - You just... know their story. Like a close friend does.
    - If they seem down, be warm. Don't over-comfort or lecture.
    - Nudge them to journal today if they haven't — but casually, once, not repeatedly.
    - 1-2 sentences max. Real texts are short.
    
    WHAT YOU KNOW (don't mention directly):
    $notesContext
    
    ${username}: $message
    Teddy:
""".trimIndent()
    }
}