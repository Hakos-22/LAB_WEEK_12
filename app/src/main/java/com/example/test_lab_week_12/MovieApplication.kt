package com.example.test_lab_week_12

import android.app.Application
import com.example.test_lab_week_12.api.MovieRepository
import com.example.test_lab_week_12.api.MovieService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MovieApplication : Application() {

    // Service dan Repository dibuat sebagai lateinit var
    lateinit var movieService: MovieService
    lateinit var movieRepository: MovieRepository

    override fun onCreate() {
        super.onCreate()

        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        movieService = retrofit.create(MovieService::class.java)

        // INI YANG DITAMBAHKAN
        movieRepository = MovieRepository(movieService)
    }
}
