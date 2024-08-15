package com.raouf.noteapp.Data.Local

import androidx.room.Database
import androidx.room.RoomDatabase



@Database(
    entities = [Note::class],
    version = 1,
    exportSchema = false
)
abstract class NoteDb : RoomDatabase(){
   abstract fun dao() : NoteDao
}