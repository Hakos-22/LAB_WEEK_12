package com.example.test_lab_week_12

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.test_lab_week_12.model.Movie

class MovieAdapter : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    private val movies = mutableListOf<Movie>()

    fun addMovies(newMovies: List<Movie>) {
        movies.clear()
        movies.addAll(newMovies)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_movie_item, parent, false)
        return MovieViewHolder(view)
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val title: TextView = view.findViewById(R.id.movie_title)
        private val date: TextView = view.findViewById(R.id.movie_release_date)
        private val poster: ImageView = view.findViewById(R.id.movie_poster)

        fun bind(movie: Movie) {
            title.text = movie.title
            date.text = movie.releaseDate

            Glide.with(itemView.context)
                .load("https://image.tmdb.org/t/p/w500${movie.posterPath}")
                .into(poster)

            itemView.setOnClickListener {
                val ctx = itemView.context
                val intent = Intent(ctx, DetailsActivity::class.java).apply {
                    putExtra(DetailsActivity.EXTRA_TITLE, movie.title)
                    putExtra(DetailsActivity.EXTRA_RELEASE, movie.releaseDate)
                    putExtra(DetailsActivity.EXTRA_OVERVIEW, movie.overview)
                    putExtra(DetailsActivity.EXTRA_POSTER, movie.posterPath)
                }
                ctx.startActivity(intent)
            }
        }
    }
}
