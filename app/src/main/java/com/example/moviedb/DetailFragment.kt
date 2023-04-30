package com.example.moviedb

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroupOverlay
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide

class DetailFragment : Fragment() {

    val viewModel: MovieViewModel by activityViewModels()

    lateinit var poster_path: ImageView
    lateinit var title: TextView
    lateinit var release_date: TextView
    lateinit var vote_average: TextView
    lateinit var overview: TextView
    lateinit var ratingBar: RatingBar


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /** Setting elements into DetailFragment **/
        poster_path = view.findViewById(R.id.editor_poster_img)
        title = view.findViewById(R.id.title_text)
        release_date = view.findViewById(R.id.release_text)
        vote_average = view.findViewById(R.id.rating_text)
        overview = view.findViewById(R.id.overview_text)
        ratingBar = view.findViewById(R.id.ratingBar)

        //Listening to rating bar change and adding the movie to DB
        ratingBar.setOnRatingBarChangeListener { ratingBar, _, _ ->
            viewModel.setMovieRating(ratingBar.rating)
            viewModel.saveMovieToDB()
        }



        viewModel.currentMovie.observe(viewLifecycleOwner) { //loading the poster using Glide
            Glide.with(this).load(it.poster_path).into(poster_path)
            title.text = it.title
            release_date.text = it.release_date
            vote_average.text = it.vote_average.toString()
            overview.text = it.overview
            ratingBar.rating = it.rating
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

}