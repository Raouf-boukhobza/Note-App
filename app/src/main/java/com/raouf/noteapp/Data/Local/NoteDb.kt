package com.raouf.noteapp.Data.Local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(
    entities = [Note::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(NoteTypeConverter::class)
abstract class NoteDb : RoomDatabase(){
   abstract fun dao() : NoteDao
}