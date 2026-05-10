package com.example.teddynotes.repository

import com.example.teddynotes.data.NoteDao
import com.example.teddynotes.model.Note
import kotlinx.coroutines.flow.Flow

class NoteRepository(private val dao : NoteDao) {
    fun getAllNotes() : Flow<List<Note>> {
        return dao.getAllNotes()
    }

    suspend fun insert(note: Note) = dao.insertNote(note)
    suspend fun delete(note: Note) = dao.deleteNote(note)

    suspend fun getNoteByDate(date : String) : Note? = dao.getNoteByDate(date)


}