package com.example.moviedb

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MovieViewModel : ViewModel() {
    val apiManager = MutableLiveData<APIManager>() //to handle API
    val savedList = MutableLiveData<Array<Movie>>() //to handle rated movies list
    val movieList = MutableLiveData<Array<Movie>>() //to handle current movie list
    val currentMovie = MutableLiveData<Movie>() //to handle current movie selected
    val database = MutableLiveData<MovieDB>() //to handle database

    init { //initializing APIManager and Lists
        apiManager.value = APIManager(this)
        savedList.value = emptyArray()
        movieList.value = emptyArray()
    }

    fun setCurrentMovie(movie: Movie) { //to assign current movie for DetailFragment
        currentMovie.value = movie
    }

    fun setCurrentMovieList(movies: ArrayList<Movie>) { //adding movies from API to 'movieList'
        val tmp_movieList = ArrayList<Movie>()
        (0 until movies.size).forEach { //selecting each movies from list passed from API
            if (database.value?.movieDAO()?.exists(movies[it].title) == true) { //checking if movie exists in Database
                movies[it].rating =
                    database.value?.movieDAO()?.getRating(movies[it].title)?.rating!! //assigning rating to movie if it exists in database
                tmp_movieList.add(movies[it]) //adding movies to a tmp list
            } else { //if movies don't exist in database
                movies[it].rating = 0.0f //set rating to 0
                tmp_movieList.add(movies[it]) //ass to tmp list
            }

        }

        movieList.value = tmp_movieList.toTypedArray() //assign the tmp list to movieList

    }

    fun saveMovieToDB() {
        if (currentMovie.value != null) { //when movie is selected for rating
            database.value?.movieDAO()?.insert(currentMovie.value!!) //add movie with rating to database
        }
    }

    fun setMovieRating(rating: Float) { //set current movie rating from Detail Screen
        currentMovie.value?.rating = rating
    }
}