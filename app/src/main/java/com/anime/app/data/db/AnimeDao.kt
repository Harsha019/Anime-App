package com.anime.app.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AnimeDao {

    @Query("SELECT * FROM anime ORDER BY rating DESC")
    suspend fun getAllAnime(): List<AnimeEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnime(list: List<AnimeEntity>)

    @Query("DELETE FROM anime")
    suspend fun clearAnime()
}
