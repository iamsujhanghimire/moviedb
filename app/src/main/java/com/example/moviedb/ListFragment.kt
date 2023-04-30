package com.example.moviedb

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class ListFragment : Fragment() {
    lateinit var viewManger: RecyclerView.LayoutManager
    lateinit var viewAdapter: RecyclerViewAdapter

    val viewModel: MovieViewModel by activityViewModels()

    lateinit var movie_recyclerView: RecyclerView

    //when movie is clicked
    val movieClickLambda = { movie: Movie ->
        viewModel.setCurrentMovie(movie) //set current movie as selected movie
        findNavController().navigate(R.id.action_global_detailFragment) //go to details
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movie_recyclerView = view.findViewById(R.id.list_recyclerView)

        viewManger = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        viewAdapter = RecyclerViewAdapter(emptyArray())

        movie_recyclerView.layoutManager = viewManger
        movie_recyclerView.adapter = viewAdapter
        viewAdapter.clickLambda = movieClickLambda

        viewModel.movieList.observe(viewLifecycleOwner) {
            viewAdapter.movieArray = it.sortedByDescending { it.rating }.toTypedArray() //sorting the recyclerList by descending order
            viewAdapter.notifyDataSetChanged()
        }

        view.findViewById<BottomNavigationView>(R.id.bottom_navigation).setOnItemSelectedListener {
            if(it.itemId == R.id.upcomingBtn){ //when navigating to upcoming movie
                viewModel.apiManager.value?.fetchUpcomingMovie() //change the callback
                findNavController().navigate(R.id.action_global_listFragment)
                return@setOnItemSelectedListener true
            }else if(it.itemId == R.id.nowPlayingBtn){ //when navigating to now playing movie
                viewModel.apiManager.value?.fetchNowShowingMovie() //change the callback
                findNavController().navigate(R.id.action_global_listFragment)
                return@setOnItemSelectedListener true
            }else{
                return@setOnItemSelectedListener true
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }
}