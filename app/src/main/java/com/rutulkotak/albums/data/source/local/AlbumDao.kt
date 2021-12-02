package com.rutulkotak.albums.data.source.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rutulkotak.albums.data.Album

/**
 * Data Access Object for the Albums table.
 */
@Dao
interface AlbumDao {

    // Observes list of albums.
    @Query("SELECT * FROM albums order by title asc")
    fun observeAlbums(): LiveData<List<Album>>

    // Select all Albums from the Albums table.
    @Query("SELECT * FROM albums order by title asc")
    fun getAlbums(): List<Album>

    // Delete all albums.
    @Query("DELETE FROM albums")
    suspend fun deleteAlbums()

    // Insert a album in the database. If the album already exists, replace it.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlbum(album: Album)

    // Insert albums list in the database. If the album already exists, replace it.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllAlbum(albumList: List<Album>)
}