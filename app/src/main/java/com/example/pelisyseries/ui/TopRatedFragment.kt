package com.example.pelisyseries.ui

import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pelisyseries.common.hide
import com.example.pelisyseries.common.show
import com.example.pelisyseries.data.models.GLOBAL
import com.example.pelisyseries.data.models.Movie
import com.example.pelisyseries.data.models.TOP_RATED
import com.example.pelisyseries.data.room.ProductDao
import com.example.pelisyseries.databinding.FragmentMoviesBinding
import com.example.pelisyseries.domain.TopRatedUseCase
import com.example.pelisyseries.ui.adapter.MovieAdapter
import com.example.pelisyseries.viewmodel.TopRatedViewModel
import kotlinx.android.synthetic.main.fragment_movies.*
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

/**
 * Segundo fragment en mostrarse en el activity principal
 * @author Axel Sanchez
 */
@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class TopRatedFragment : Fragment() {

    private val repository: ProductDao by inject()
    private val topRatedUseCase: TopRatedUseCase by inject()

    private val viewModel: TopRatedViewModel by viewModels(
        factoryProducer = { TopRatedViewModel.TopRatedViewModelFactory(topRatedUseCase, repository) }
    )

    private lateinit var viewAdapter: MovieAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

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

        setupViewModelAndObserveMovies()
    }

    private fun setupViewModelAndObserveMovies() {
        viewModel.getListMoviesLiveData().observe(viewLifecycleOwner, {
            binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (viewAdapter.getItems().isNullOrEmpty()) {
                        binding.emptyState.show()
                        binding.emptyStateFilter.show()
                    } else {
                        binding.emptyState.hide()
                        binding.emptyStateFilter.hide()
                    }
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    binding.emptyState.hide()
                    binding.emptyStateFilter.hide()
                    viewAdapter.filter.filter(newText)
                    return false
                }
            })

            binding.searchOnline.setOnClickListener {
                viewModel.getListMoviesFromSearch(binding.search.query.toString())

                binding.emptyState.hide()
                binding.emptyStateFilter.hide()
                progress.playAnimation()
                progress.show()

                setupViewModelAndObserveSearch()
            }

            binding.search.setOnCloseListener {
                setAdapter(it)
                binding.emptyState.hide()
                binding.emptyStateFilter.hide()
                false
            }

            progress.cancelAnimation()
            progress.hide()
            recyclerview.show()

            for (movie in it) {
                movie?.let {
                    movie.origen = TOP_RATED
                    lifecycleScope.launch {
                        repository.insertMovie(movie)
                    }
                }
            }

            setAdapter(it)
        })
    }

    private fun setupViewModelAndObserveSearch() {
        viewModel.getListMoviesLiveDataFromSearch().observe(viewLifecycleOwner, {
            binding.emptyState.hide()
            binding.emptyStateFilter.hide()
            progress.cancelAnimation()
            progress.hide()
            recyclerview.show()

            binding.search.setOnCloseListener {
                lifecycleScope.launch {
                    setAdapter(repository.getMovieByOrigin(TOP_RATED))
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
        })
    }

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