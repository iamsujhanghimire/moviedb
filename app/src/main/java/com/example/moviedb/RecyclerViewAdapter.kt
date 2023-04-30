package com.example.moviedb

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class RecyclerViewAdapter(var movieArray: Array<Movie>) :
    RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder>() {

    lateinit var clickLambda: (Movie) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val viewItem =
            LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        return RecyclerViewHolder(viewItem)
    }

    override fun getItemCount(): Int {
        return movieArray.size
    }

    class RecyclerViewHolder(val view: View) :
        RecyclerView.ViewHolder(view) {
        fun bind(movie: Movie, clickLambda: (Movie) -> Unit) { //assigning values to RecyclerView
            view.findViewById<TextView>(R.id.item_name).text = movie.title
            Glide.with(view).load(movie.poster_path)
                .into(view.findViewById(R.id.editor_poster_img))
            view.findViewById<RatingBar>(R.id.ratingBar).rating = movie.rating
            view.setOnClickListener{
                clickLambda(movie)
            }
        }
    }


    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.bind(movieArray[position], clickLambda)
    }
}