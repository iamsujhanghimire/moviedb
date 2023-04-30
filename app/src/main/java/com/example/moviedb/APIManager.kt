package com.example.moviedb

import android.util.Log
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

class APIManager(val movieViewModel: MovieViewModel) { //2
    private val apiURL = "https://api.themoviedb.org/" //base URL for the MovieDB
    private val apiKey = "4a21cf2e94d8d4296f00bd5cf88b67be" //myAPIKey

    val retrofit = Retrofit.Builder() //set up retrofit for the API
        .baseUrl(apiURL)
        .build()

    val service = retrofit.create(MovieService::class.java)

    interface MovieService {
        //setting queries to get upcoming movie
        @GET("3/movie/upcoming?")
        fun getUpcomingMovie(
            @Query("api_key") api_key: String
        ): Call<ResponseBody>

        //setting queries to get upcoming movie
        @GET("3/movie/now_playing?")
        fun getNowPlayingMovie(
            @Query("api_key") api_key: String
        ): Call<ResponseBody>
    }

    //to parse the json and add it into the movieList
    fun decodeJson(json: String) {
        val data = JSONObject(json).getJSONArray("results")
        val movies = ArrayList<Movie>()
        for (i in 0 until 20) {
            movies.add(
                Movie(
                    data.getJSONObject(i).getString("title"),
                    data.getJSONObject(i).getString("release_date"),
                    data.getJSONObject(i).getDouble("vote_average"),
                    data.getJSONObject(i).getString("overview"),
                    "https://image.tmdb.org/t/p/w300" + data.getJSONObject(i).getString("poster_path")
                )
            )
        }
        movieViewModel.setCurrentMovieList(movies)
    }

    //to get upcoming movies from the API
    fun fetchUpcomingMovie() {
        val call = service.getUpcomingMovie(apiKey)
        call.enqueue(MovieCallback())
    }

    //to get now showing movies from the API
    fun fetchNowShowingMovie() {
        val call = service.getNowPlayingMovie(apiKey)
        call.enqueue(MovieCallback())
    }

    //Callback
    inner class MovieCallback() :
        Callback<ResponseBody> {
        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
        }

        override fun onResponse(
            call: Call<ResponseBody>,
            response: Response<ResponseBody>
        ) {
            if (response.isSuccessful) {
                response.body()?.let {
                    decodeJson(it.string())
                }
            }
        }
    }

}