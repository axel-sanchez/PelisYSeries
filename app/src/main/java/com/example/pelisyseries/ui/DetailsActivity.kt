package com.example.pelisyseries.ui

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.pelisyseries.R
import com.example.pelisyseries.data.models.Movie
import com.example.pelisyseries.data.models.Video
import com.example.pelisyseries.databinding.FragmentDetailsMovieBinding
import com.example.pelisyseries.domain.DetailsUseCase
import com.example.pelisyseries.ui.adapter.BASE_URL_IMAGEN
import com.example.pelisyseries.viewmodel.DetailsViewModel
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

const val API_KEY_YOUTUBE = "AIzaSyCQ6v66wKoSuumIAHFzEUfan3MIS9gpRRc"

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
/**
 * Activity que muestra los detalles de una pelicula
 * @author Axel Sanchez
 */
class DetailsActivity : YouTubeBaseActivity() {

    private lateinit var binding: FragmentDetailsMovieBinding

    private lateinit var viewModel: DetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentDetailsMovieBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        viewModel = ViewModelProviders.of(
            MainFragment.copyFragment,
            DetailsViewModel.DetailsViewModelFactory(DetailsUseCase())
        ).get(DetailsViewModel::class.java)

        binding.image.transitionName = "main_poster"

        val idMovie = intent.extras!!.getInt("idMovie")

        viewModel.getDetailsMovie(idMovie)

        viewModel.getVideo(idMovie)

        setupViewModelAndObserve()
    }

    /**
     * Creo un observer para que este observando al [DetailsViewModel] y cuando obtenga los datos actualizar la vista
     */
    private fun setupViewModelAndObserve() {
        try {
            val daysObserver = Observer<Movie?> {
                it?.let {
                    binding.title.text = it.title

                    binding.calificacion.text = it.vote_average.toString()
                    binding.categoria.text = it.origen
                    binding.overview.text = it.overview
                    it.release_date?.let { releaseDate ->
                        binding.date.text = releaseDate.substring(0, 4)
                    }
                    it.adult?.let { adult ->
                        if (adult) binding.edad.visibility = View.VISIBLE
                        else binding.edad.visibility = View.GONE
                    }

                    Picasso.with(this)
                        .load("$BASE_URL_IMAGEN${it.poster_path}")
                        .noFade()
                        .into(binding.image, object : Callback {
                            override fun onSuccess() {
                                startPostponedEnterTransition()
                            }

                            override fun onError() {
                                startPostponedEnterTransition()
                            }
                        })
                }
            }
            val observerVideo = Observer<Video?> {
                var key: String?
                it?.let {
                    key = it.key
                    key?.let {
                        binding.play.visibility = View.VISIBLE
                        binding.play.setOnClickListener {
                            findViewById<YouTubePlayerView>(R.id.youtube).initialize(
                                API_KEY_YOUTUBE,
                                object : YouTubePlayer.OnInitializedListener {
                                    override fun onInitializationSuccess(
                                        p0: YouTubePlayer.Provider?,
                                        youtubePlayer: YouTubePlayer?,
                                        p2: Boolean
                                    ) {
                                        youtubePlayer?.let { it -> it.loadVideo(key) }
                                        findViewById<YouTubePlayerView>(R.id.youtube).visibility =
                                            View.VISIBLE
                                        binding.play.visibility = View.GONE
                                    }

                                    override fun onInitializationFailure(
                                        p0: YouTubePlayer.Provider?,
                                        p1: YouTubeInitializationResult?
                                    ) {
                                        Log.e("error", "al cargar el video")
                                    }

                                })
                        }
                    } ?: kotlin.run { binding.play.visibility = View.GONE }
                } ?: kotlin.run { binding.play.visibility = View.GONE }
            }
            viewModel.getDetailsMovieLiveData().observeForever(daysObserver)
            viewModel.getLiveDataVideo().observeForever(observerVideo)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onBackPressed() {
        finishAfterTransition()
    }
}