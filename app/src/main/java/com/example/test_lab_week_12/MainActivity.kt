package com.example.test_lab_week_12

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.test_lab_week_12.api.MovieViewModel
import com.google.android.material.snackbar.Snackbar
import java.util.Calendar

class MainActivity : ComponentActivity() {

    private val movieAdapter = MovieAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.movie_list)
        recyclerView.adapter = movieAdapter

        // <-- Perbaikan: akses property movieRepository (lowerCamelCase)
        // Pastikan MovieApplication memiliki: val movieRepository: com.example.test_lab_week_12.api.MovieRepository
        val repo = (application as MovieApplication).movieRepository

        val movieViewModel = ViewModelProvider(
            this,
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    @Suppress("UNCHECKED_CAST")
                    return MovieViewModel(repo) as T
                }
            }
        )[MovieViewModel::class.java]

        movieViewModel.popularMovies.observe(this) { movies ->
            val year = Calendar.getInstance().get(Calendar.YEAR).toString()

            movieAdapter.addMovies(
                movies
                    .filter { it.releaseDate?.startsWith(year) == true }
                    .sortedByDescending { it.popularity }
            )
        }

        movieViewModel.error.observe(this) { error ->
            if (!error.isNullOrBlank()) {
                Snackbar.make(recyclerView, error, Snackbar.LENGTH_LONG).show()
            }
        }
    }
}
