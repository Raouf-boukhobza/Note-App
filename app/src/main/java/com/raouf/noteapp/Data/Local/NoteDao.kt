package com.raouf.noteapp.Data.Local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow


@Dao
interface NoteDao {

    @Upsert
    suspend fun addNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("SELECT * FROM  note ")
    fun selectAllNote() : Flow<List<Note>>

    @Query("SELECT * FROM note WHERE type = :noteType ")
    fun selectNoteWithType(noteType: Sort) : Flow<List<Note>>


    @Query("SELECT * FROM note WHERE id = :id")
    fun selectNoteWithId(id : Int) : Note
}