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

    suspend fun getReply(message: String, notes: List<Note>): String {

        val prompt = buildPrompt(message, notes)

        return try {

            val response = model.generateContent(
                content { text(prompt) })
            response.text ?: "Teddy is thinking 🐻"

        } catch (e: Exception) {
            Log.e("GEMINI", "API FAILED", e)
            "Teddy had trouble connecting 🥲"
        }
    }

    private fun buildPrompt(message: String, notes: List<Note>): String {

        val notesContext = if (notes.isEmpty()) {
            "No previous notes available."
        } else {
            notes.takeLast(4).joinToString("\n") { note ->
                "Date: ${note.date}, Mood: ${note.mood}, Title: ${note.title}, Entry: ${note.content}"
            }
        }

        return """
    You are Teddy 🐻 — a close friend who happens to be an amazing active listener.
    You've read their journal and know what they've been going through.
    
    Just be a good friend. You know what that means.
    
    Keep it to 1-2 sentences like a real text.
    
    JOURNAL CONTEXT:
    $notesContext
    
    USER MESSAGE: $message
""".trimIndent()
    }
}