package com.example.teddynotes.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.teddynotes.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note : Note)

    @Delete
    suspend fun deleteNote(note : Note)

    @Query("SELECT * FROM notes ORDER BY date DESC")
    fun getAllNotes() : Flow<List<Note>>

    @Query("SELECT * FROM notes WHERE date = :date LIMIT 1")
    suspend fun getNoteByDate(date:String) : Note?

    @Query("SELECT * FROM notes WHERE date = :date")
    suspend fun getNoteById(date:String) : Note?

    @Query("SELECT * FROM notes WHERE date >= :sevenDaysAgo")
    suspend fun getNotesFromLastWeek(sevenDaysAgo: String) : List<Note>

    @Query("DELETE FROM notes")
    suspend fun deleteAllNotes()

}