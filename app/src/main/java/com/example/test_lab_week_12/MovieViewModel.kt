package com.example.test_lab_week_12.api

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test_lab_week_12.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class MovieViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    // StateFlow
    private val _popularMovies = MutableStateFlow(emptyList<Movie>())
    val popularMovies: StateFlow<List<Movie>> = _popularMovies

    private val _error = MutableStateFlow("")
    val error: StateFlow<String> = _error

    init {
        fetchPopularMovies()
    }

    private fun fetchPopularMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            movieRepository.fetchMovies()
                .catch { exception ->
                    _error.value = "An exception occurred: ${exception.message}"
                }
                .collect { movies ->
                    // 🔥 Sorting descending by popularity
                    val sortedMovies = movies.sortedByDescending { it.popularity }
                    _popularMovies.value = sortedMovies
                }
        }
    }
}
