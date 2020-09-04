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
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.SearchView
import androidx.cardview.widget.CardView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.example.pelisyseries.R
import com.example.pelisyseries.data.TableMovie
import com.example.pelisyseries.data.models.Movie
import com.example.pelisyseries.data.repository.GLOBAL
import com.example.pelisyseries.data.repository.GenericRepository
import com.example.pelisyseries.data.repository.POPULAR
import com.example.pelisyseries.data.repository.TOP_RATED
import com.example.pelisyseries.domain.TopRatedUseCase
import com.example.pelisyseries.ui.adapter.MovieAdapter
import com.example.pelisyseries.ui.customs.BaseFragment
import com.example.pelisyseries.viewmodel.TopRatedViewModel
import com.example.pelisyseries.viewmodel.TopRatedViewModelFactory
import kotlinx.android.synthetic.main.fragment_movies.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

/**
 * Segundo fragment en mostrarse en el activity principal
 * @author Axel Sanchez
 */
@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class TopRatedFragment : BaseFragment() {

    private val repository: GenericRepository by inject()

    private val viewModel: TopRatedViewModel by lazy {
        ViewModelProviders.of(
            requireActivity(),
            TopRatedViewModelFactory(TopRatedUseCase())
        ).get(TopRatedViewModel::class.java)
    }

    private lateinit var viewAdapter: MovieAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    private lateinit var progress: LottieAnimationView
    private lateinit var recyclerview: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var emptyState: CardView
    private lateinit var emptyStateFilter: LinearLayout
    private lateinit var searchOnline: Button

    override fun onBackPressFragment() = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progress = view.findViewById(R.id.progress)
        recyclerview = view.findViewById(R.id.recyclerview)
        searchView = view.findViewById(R.id.search)
        emptyState = view.findViewById(R.id.empty_state)
        emptyStateFilter = view.findViewById(R.id.empty_state_filter)
        searchOnline = view.findViewById(R.id.search_online)

        CoroutineScope(Main).launch {
            viewModel.getListMovies(repository)
        }

        setupViewModelAndObserve()
    }

    /**
     * Configuramos el viewModel para estar a la escucha de nuestra petición a la api de peliculas
     * Y también va a estar a la escucha de cuando buscamos el video de la pelicula
     */
    private fun setupViewModelAndObserve() {
        val daysObserver = Observer<List<Movie>> {
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (viewAdapter.getItems().isNullOrEmpty()) {
                        emptyState.showView(true)
                        emptyStateFilter.showView(true)
                    } else {
                        emptyState.showView(false)
                        emptyStateFilter.showView(false)
                    }
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    emptyState.showView(false)
                    emptyStateFilter.showView(false)
                    viewAdapter.filter.filter(newText)
                    return false
                }
            })

            searchOnline.setOnClickListener {
                CoroutineScope(Main).launch {
                    viewModel.getListMoviesFromSearch(searchView.query.toString())
                }
                emptyState.showView(false)
                emptyStateFilter.showView(false)
                progress.playAnimation()
                progress.showView(true)
            }

            searchView.setOnCloseListener {
                emptyState.showView(false)
                emptyStateFilter.showView(false)
                false
            }

            progress.cancelAnimation()
            progress.showView(false)
            recyclerview.showView(true)

            for (movie in it) {
                movie.origen = TOP_RATED
                repository.insert(movie)
            }

            setAdapter(it)
        }

        val searchObserver = Observer<List<Movie>> {
            emptyState.showView(false)
            emptyStateFilter.showView(false)
            progress.cancelAnimation()
            progress.showView(false)
            recyclerview.showView(true)

            searchView.setOnCloseListener {
                setAdapter(
                    repository.getMovie(
                        arrayOf(TableMovie.Columns.COLUMN_NAME_ORIGEN_LIST), arrayOf(
                            TOP_RATED
                        ), null
                    )
                )
                false
            }

            for (movie in it) {
                movie.origen = GLOBAL
                repository.insert(movie)
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
    private fun setAdapter(movies: List<Movie>) {
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

    /**
     * Abro el activity con los detalles de cada pelicula
     * @param [item] le paso la pelicula
     */
    private fun itemClick(item: Movie) {
        val intent = Intent(context, DetailsActivity::class.java)
        intent.putExtra("idMovie", item.id)
        intent.putExtra("poster_path", item.poster_path)
        val options =
            ActivityOptions.makeSceneTransitionAnimation(activity, item.imageView, "main_poster")
        startActivity(intent, options.toBundle())
    }
}