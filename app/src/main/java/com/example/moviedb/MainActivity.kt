package com.example.moviedb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    val viewModel:MovieViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Show Now Playing movie when starting app and initialize DB
        viewModel.apiManager.value?.fetchNowShowingMovie()
        viewModel.database.value = MovieDB.getDBObject(applicationContext)


    }

    /** For Action Bar Menu **/
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        if (item.itemId == R.id.nowPlayingBtn) {
            viewModel.apiManager.value?.fetchNowShowingMovie()
            findNavController(R.id.nav_host_frag).navigate(R.id.action_global_listFragment)
            return true
        }else if(item.itemId == R.id.upcomingBtn){
            viewModel.apiManager.value?.fetchUpcomingMovie()
            findNavController(R.id.nav_host_frag).navigate(R.id.action_global_listFragment)
            return true
        }
        else {
            return super.onOptionsItemSelected(item)
        }
    }
    /** End of Action Bar Menu **/
}