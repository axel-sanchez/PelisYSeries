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
import com.example.pelisyseries.data.repository.GenericRepository
import com.example.pelisyseries.databinding.FragmentDetailsMovieBinding
import com.example.pelisyseries.domain.DetailsUseCase
import com.example.pelisyseries.ui.adapter.BASE_URL_IMAGEN
import com.example.pelisyseries.viewmodel.DetailsViewModel
import com.example.pelisyseries.viewmodel.DetailsViewModelFactory
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

const val API_KEY_YOUTUBE = "AIzaSyCQ6v66wKoSuumIAHFzEUfan3MIS9gpRRc"
@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
/**
 * Activity que muestra los detalles de una pelicula
 * @author Axel Sanchez
 */
class DetailsActivity : YouTubeBaseActivity() {

    private val repository: GenericRepository by inject()

    private lateinit var binding: FragmentDetailsMovieBinding

    private lateinit var viewModel: DetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentDetailsMovieBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        viewModel = ViewModelProviders.of(MainFragment.copyFragment, DetailsViewModelFactory(DetailsUseCase())).get(DetailsViewModel::class.java)

        binding.image.transitionName = "main_poster"

        val idMovie = intent.extras!!.getInt("idMovie")
        val posterPath = intent.extras!!.getString("poster_path")

        Picasso.with(this)
            .load("$BASE_URL_IMAGEN${posterPath}")
            .noFade()
            .into(binding.image, object : Callback {
                override fun onSuccess() {
                    startPostponedEnterTransition()
                }

                override fun onError() {
                    startPostponedEnterTransition()
                }
            })

        CoroutineScope(Dispatchers.Main).launch {
            viewModel.getDetailsMovie(repository, idMovie)
        }

        setupViewModelAndObserve()
    }

    /**
     * Creo un observer para que este observando al [DetailsViewModel] y cuando obtenga los datos actualizar la vista
     */
    private fun setupViewModelAndObserve() {
        val daysObserver = Observer<Movie> {
            binding.title.text = it.title

            binding.calificacion.text = it.vote_average.toString()
            binding.categoria.text = it.origen
            binding.overview.text = it.overview
            binding.date.text = it.release_date.substring(0, 4)
            if (it.adult) binding.edad.visibility = View.VISIBLE
            else binding.edad.visibility = View.GONE

            var key = it.keyVideo

            binding.play.setOnClickListener {
                findViewById<YouTubePlayerView>(R.id.youtube).initialize(API_KEY_YOUTUBE, object : YouTubePlayer.OnInitializedListener {
                    override fun onInitializationSuccess(p0: YouTubePlayer.Provider?, youtubePlayer: YouTubePlayer?, p2: Boolean) {
                        youtubePlayer!!.loadVideo(key)
                        findViewById<YouTubePlayerView>(R.id.youtube).visibility = View.VISIBLE
                        binding.play.visibility = View.GONE
                    }

                    override fun onInitializationFailure(p0: YouTubePlayer.Provider?, p1: YouTubeInitializationResult?) {
                        Log.e("error" ,"al cargar el video")
                    }

                })
            }
        }
        viewModel.getDetailsMovieLiveData().observeForever(daysObserver)
    }

    override fun onBackPressed() {
        finishAfterTransition()
    }
}