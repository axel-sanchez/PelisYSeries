package com.example.pelisyseries.ui

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.airbnb.lottie.LottieAnimationView
import com.example.pelisyseries.R
import com.example.pelisyseries.data.models.Movie
import com.example.pelisyseries.data.repository.GenericRepository
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

const val API_KEY_YOUTUBE = "AIzaSyCQ6v66wKoSuumIAHFzEUfan3MIS9gpRRc"
@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
/**
 * Activity que muestra los detalles de una pelicula
 * @author Axel Sanchez
 */
class DetailsActivity : YouTubeBaseActivity() {

    private lateinit var repository: GenericRepository

    private lateinit var viewModel: DetailsViewModel

    private lateinit var image: ImageView
    private lateinit var title: TextView
    private lateinit var categoria: TextView
    private lateinit var calificacion: TextView
    private lateinit var edad: TextView
    private lateinit var overview: TextView
    private lateinit var date: TextView
    private lateinit var play: Button
    private lateinit var youtube: YouTubePlayerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_details_movie)

        repository = GenericRepository.getInstance(this)

        image = findViewById(R.id.image)
        title = findViewById(R.id.title)
        categoria = findViewById(R.id.categoria)
        calificacion = findViewById(R.id.calificacion)
        edad = findViewById(R.id.edad)
        overview = findViewById(R.id.overview)
        date = findViewById(R.id.date)
        play = findViewById(R.id.play)
        youtube = findViewById(R.id.youtube)

        viewModel = ViewModelProviders.of(MainFragment.copyFragment, DetailsViewModelFactory(DetailsUseCase())).get(DetailsViewModel::class.java)

        image.transitionName = "main_poster"

        val idMovie = intent.extras!!.getInt("idMovie")

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
            title.text = it.title

            calificacion.text = it.vote_average.toString()
            categoria.text = it.origen
            overview.text = it.overview
            date.text = it.release_date.substring(0, 4)
            if (it.adult) edad.visibility = View.VISIBLE
            else edad.visibility = View.GONE

            var key = it.keyVideo

            play.setOnClickListener {
                youtube.initialize(API_KEY_YOUTUBE, object : YouTubePlayer.OnInitializedListener {
                    override fun onInitializationSuccess(p0: YouTubePlayer.Provider?, youtubePlayer: YouTubePlayer?, p2: Boolean) {
                        youtubePlayer!!.loadVideo(key)
                        youtube.visibility = View.VISIBLE
                        play.visibility = View.GONE
                    }

                    override fun onInitializationFailure(p0: YouTubePlayer.Provider?, p1: YouTubeInitializationResult?) {
                        Log.e("error" ,"al cargar el video")
                    }

                })
            }

            Picasso.with(this)
                .load("$BASE_URL_IMAGEN${it.poster_path}")
                .noFade()
                .into(image, object : Callback {
                    override fun onSuccess() {
                        startPostponedEnterTransition()
                    }

                    override fun onError() {
                        startPostponedEnterTransition()
                    }
                })
        }
        viewModel.getDetailsMovieLiveData().observeForever(daysObserver)
    }

    override fun onBackPressed() {
        finishAfterTransition()
    }
}