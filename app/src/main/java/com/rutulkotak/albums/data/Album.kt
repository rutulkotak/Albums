package com.rutulkotak.albums.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Album entity
 *
 * @param title title of the album
 * @param userId userId of the album
 * @param id id of the album
 */
@Entity(tableName = "albums")
data class Album @JvmOverloads constructor(
    @ColumnInfo(name = "title") var title: String = "",
    @ColumnInfo(name = "userId") var userId: Int = 0,
    @PrimaryKey @ColumnInfo(name = "id") var id: Int = 0
) {

    val titleForList: String
        get() = if (title.isNotEmpty()) title else "Not Available"
}