package com.example.teddynotes.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.teddynotes.model.Note

@Database(entities = [Note::class], version = 1)
@TypeConverters(MoodConverter::class)
abstract class TeddyDatabase : RoomDatabase() {
    abstract fun noteDao() : NoteDao

    companion object{
        @Volatile
        private var INSTANCE : TeddyDatabase ?=null

        fun getDatabase(context : Context) : TeddyDatabase{
            return INSTANCE ?: synchronized(this){
                Room.databaseBuilder(
                    context.applicationContext,
                    TeddyDatabase::class.java,
                    "teddy_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also {INSTANCE = it }
            }
        }
    }
}