package com.rutulkotak.albums.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rutulkotak.albums.data.Album

@Database(entities = [Album::class], version = 1, exportSchema = false)
abstract class AlbumsDatabase : RoomDatabase() {

    abstract fun albumDao(): AlbumDao
}