package com.raouf.noteapp.Data.Local

import androidx.room.TypeConverter

class NoteTypeConverter {


    @TypeConverter
    fun fromNoteType(noteType: NoteType): String {
        return noteType.name // Convert enum to its name as a string
    }

    @TypeConverter
    fun toNoteType(noteTypeString: String): NoteType {
        return NoteType.valueOf(noteTypeString) // Convert string back to the corresponding enum value
    }

}