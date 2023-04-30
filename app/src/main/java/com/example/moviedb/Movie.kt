package com.example.moviedb

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movieTable")

class Movie (@PrimaryKey var title:String, var release_date:String, var vote_average:Double, var overview:String, var poster_path:String){

    var rating = 0.0f
}