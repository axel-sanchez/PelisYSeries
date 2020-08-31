package com.example.pelisyseries.ui

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.pelisyseries.R
import com.example.pelisyseries.data.models.Movie
import com.example.pelisyseries.data.repository.GenericRepository
import com.example.pelisyseries.domain.DetailsUseCase
import com.example.pelisyseries.ui.adapter.BASE_URL_IMAGEN
import com.example.pelisyseries.viewmodel.DetailsViewModel
import com.example.pelisyseries.viewmodel.DetailsViewModelFactory
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class DetailsActivity : AppCompatActivity() {

    private lateinit var repository: GenericRepository

    private val viewModel: DetailsViewModel by lazy { ViewModelProviders.of(this, DetailsViewModelFactory(DetailsUseCase())).get(DetailsViewModel::class.java) }

    private lateinit var image: ImageView
    private lateinit var title: TextView
    private lateinit var categoria: TextView
    private lateinit var calificacion: TextView
    private lateinit var edad: TextView
    private lateinit var overview: TextView

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
        image.transitionName = "main_poster"

        val idMovie = intent.extras!!.getInt("idMovie")

        CoroutineScope(Dispatchers.Main).launch {
            viewModel.getDetailsMovie(repository, idMovie)
        }

        setupViewModelAndObserve()
    }

    private fun setupViewModelAndObserve() {
        val daysObserver = Observer<Movie> {
            title.text = it.title

            calificacion.text = it.vote_average.toString()
            categoria.text = it.origen
            overview.text = it.overview
            if(it.adult) edad.visibility = View.VISIBLE
            else edad.visibility = View.GONE

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
