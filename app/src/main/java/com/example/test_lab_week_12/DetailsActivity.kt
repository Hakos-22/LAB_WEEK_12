package com.example.test_lab_week_12

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.test_lab_week_12.databinding.ActivityDetailsBinding

class DetailsActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_TITLE = "extra_title"
        const val EXTRA_RELEASE = "extra_release"
        const val EXTRA_OVERVIEW = "extra_overview"
        const val EXTRA_POSTER = "extra_poster"
    }

    private lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.titleText.text = intent.getStringExtra(EXTRA_TITLE)
        binding.releaseText.text = intent.getStringExtra(EXTRA_RELEASE)
        binding.overviewText.text = intent.getStringExtra(EXTRA_OVERVIEW)

        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w500" + intent.getStringExtra(EXTRA_POSTER))
            .into(binding.moviePoster)
    }
}
