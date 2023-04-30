package com.example.moviedb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Movie::class], version = 1)

abstract class MovieDB: RoomDatabase() {
    abstract fun movieDAO(): MovieDAO

    companion object {
        private var INSTANT: MovieDB? = null

        fun getDBObject(context: Context): MovieDB? {
            if (INSTANT == null) {
                synchronized(MovieDB::class.java) {
                    INSTANT = Room.databaseBuilder(context, MovieDB::class.java, "movieDB")
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANT
        }
    }
}