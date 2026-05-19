package com.example.teddynotes.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.teddynotes.data.local.TeddyDatabase
import com.example.teddynotes.model.Mood
import com.example.teddynotes.model.Note
import com.example.teddynotes.repository.NoteRepository
import com.google.firebase.analytics.FirebaseAnalytics
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class NoteViewModel(application: Application, private val analytics: FirebaseAnalytics) : AndroidViewModel(application) {
    private val dao = TeddyDatabase.getDatabase(application).noteDao()

    private val repository = NoteRepository(dao)

    val allNotes: StateFlow<List<Note>> = repository.getAllNotes().stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList()
    )

    fun addNote(note: Note) {
        viewModelScope.launch {
            val existing = repository.getNoteByDate(note.date)
            repository.insert(note)
            if(existing == null) analytics.logEvent("note_created",null)
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            repository.delete(note)
        }
    }

    suspend fun getNoteByDate(date: String): Note? {
        return repository.getNoteByDate(date)
    }

    suspend fun getMoodThisWeek(): Mood? {
        val formatter = java.time.format.DateTimeFormatter.ofPattern("dd MMM yyyy")
        val sevenDaysAgo = java.time.LocalDate.now().minusDays(7).format(formatter)
        val notes = repository.getNotesFromLastWeek(sevenDaysAgo)
        return notes.groupBy { it.mood }.maxByOrNull { it.value.size }?.key

    }

    fun deleteAllNotes() {
        viewModelScope.launch {
            repository.deleteAllNotes()
        }
    }

}