package com.example.pelisyseries.ui

import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.SearchView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.example.pelisyseries.R
import com.example.pelisyseries.common.hide
import com.example.pelisyseries.common.show
import com.example.pelisyseries.data.models.GLOBAL
import com.example.pelisyseries.data.models.Movie
import com.example.pelisyseries.data.models.UPCOMING
import com.example.pelisyseries.data.room.Database
import com.example.pelisyseries.data.room.ProductDao
import com.example.pelisyseries.databinding.FragmentMoviesBinding
import com.example.pelisyseries.domain.UpcomingUseCase
import com.example.pelisyseries.ui.adapter.MovieAdapter
import com.example.pelisyseries.viewmodel.UpcomingViewModel
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

/**
 * Tercer fragment en mostrarse en el activity principal
 * @author Axel Sanchez
 */
@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class UpcomingFragment : Fragment() {

    private val repository: ProductDao by inject()

    private val viewModel: UpcomingViewModel by lazy {
        ViewModelProviders.of(requireActivity(), UpcomingViewModel.UpcomingViewModelFactory(UpcomingUseCase())).get(UpcomingViewModel::class.java)
    }

    private lateinit var viewAdapter: MovieAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    private lateinit var progress: LottieAnimationView
    private lateinit var recyclerview: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var emptyState: CardView
    private lateinit var emptyStateFilter: LinearLayout
    private lateinit var searchOnline: Button

    private var fragmentMainBinding: FragmentMoviesBinding? = null
    private val binding get() = fragmentMainBinding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        fragmentMainBinding = FragmentMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentMainBinding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progress = view.findViewById(R.id.progress)
        recyclerview = view.findViewById(R.id.recyclerview)
        searchView = view.findViewById(R.id.search)
        emptyState = view.findViewById(R.id.empty_state)
        emptyStateFilter = view.findViewById(R.id.empty_state_filter)
        searchOnline = view.findViewById(R.id.search_online)

        viewModel.getListMovies(repository)

        setupViewModelAndObserve()
    }

    /**
     * Configuramos el viewModel para estar a la escucha de nuestra petición a la api de peliculas
     * Y también va a estar a la escucha de cuando buscamos el video de la pelicula
     */
    private fun setupViewModelAndObserve() {
        val daysObserver = Observer<List<Movie?>> {
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (viewAdapter.getItems().isNullOrEmpty()) {
                        emptyState.show()
                        emptyStateFilter.show()
                    } else {
                        emptyState.hide()
                        emptyStateFilter.hide()
                    }
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    emptyState.hide()
                    emptyStateFilter.hide()
                    viewAdapter.filter.filter(newText)
                    return false
                }
            })

            searchOnline.setOnClickListener {
                viewModel.getListMoviesFromSearch(searchView.query.toString())
                emptyState.hide()
                emptyStateFilter.hide()
                progress.playAnimation()
                progress.show()
            }

            searchView.setOnCloseListener {
                emptyState.hide()
                emptyStateFilter.hide()
                false
            }

            progress.cancelAnimation()
            progress.hide()
            recyclerview.show()

            for (movie in it) {
                movie?.let {
                    movie.origen = UPCOMING
                    lifecycleScope.launch {
                        repository.insertMovie(movie)
                    }
                }
            }

            setAdapter(it)
        }

        val searchObserver = Observer<List<Movie?>> {
            emptyState.hide()
            emptyStateFilter.hide()
            progress.cancelAnimation()
            progress.hide()
            recyclerview.show()

            searchView.setOnCloseListener {
                lifecycleScope.launch {
                    setAdapter(repository.getMovieByOrigin(UPCOMING))
                }
                false
            }

            for (movie in it) {
                movie?.let {
                    movie.origen = GLOBAL
                    lifecycleScope.launch {
                        repository.insertMovie(movie)
                    }
                }
            }

            setAdapter(it)
        }

        viewModel.getListMoviesLiveData().observe(viewLifecycleOwner, daysObserver)
        viewModel.getListMoviesLiveDataFromSearch().observe(viewLifecycleOwner, searchObserver)
    }

    /**
     * Adaptamos el recyclerview de peliculas
     * @param [movies] listado de peliculas
     */
    private fun setAdapter(movies: List<Movie?>) {

        viewAdapter = MovieAdapter(movies) { itemClick(it) }

        viewManager = GridLayoutManager(this.requireContext(), 2)

        recyclerview.apply {
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter

        }
    }

    private fun itemClick(item: Movie) {
        val intent = Intent(context, DetailsActivity::class.java)
        intent.putExtra("idMovie", item.id)
        val options =
            ActivityOptions.makeSceneTransitionAnimation(activity, item.imageView, "main_poster")
        startActivity(intent, options.toBundle())
    }
}