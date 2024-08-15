package com.raouf.noteapp.di.module

import android.content.Context
import androidx.room.Room
import com.raouf.noteapp.Data.Local.NoteDao
import com.raouf.noteapp.Data.Local.NoteDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class RoomModule {

    @Provides
    fun provideDB(@ApplicationContext context: Context) : NoteDb {
        return Room.databaseBuilder(
            context,
            NoteDb::class.java,
            "NoteDB"
        ).build()
    }

    @Provides
    fun provideDao(noteDb: NoteDb) : NoteDao{
        return noteDb.dao()
    }
}