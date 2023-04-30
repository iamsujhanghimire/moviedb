package com.example.moviedb

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import java.util.Objects

@Dao
interface MovieDAO {
    @Query("SELECT * FROM movieTable")
    fun getAll():List<Movie>

    @Query("SELECT * FROM movieTable WHERE title = :title")
    fun getRating(title: String):Movie

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movie: Movie)

    @Query("SELECT EXISTS (SELECT 1 FROM movieTable WHERE title = :title)")
    fun exists(title: String): Boolean

}