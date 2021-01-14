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
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pelisyseries.common.hide
import com.example.pelisyseries.common.show
import com.example.pelisyseries.data.models.GLOBAL
import com.example.pelisyseries.data.models.Movie
import com.example.pelisyseries.data.models.POPULAR
import com.example.pelisyseries.data.room.Database
import com.example.pelisyseries.data.room.ProductDao
import com.example.pelisyseries.databinding.FragmentMoviesBinding
import com.example.pelisyseries.domain.PopularUseCase
import com.example.pelisyseries.ui.adapter.MovieAdapter
import com.example.pelisyseries.viewmodel.PopularViewModel
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
/**
 * Fragment que muestra el primer listado de movies en el viewPager
 * @author Axel Sanchez
 */
class PopularFragment : Fragment() {

    private val repository: ProductDao by inject()
    private val popularUseCase: PopularUseCase by inject()

    private val viewModel: PopularViewModel by viewModels(
        factoryProducer = { PopularViewModel.PopularViewModelFactory(popularUseCase) }
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

        viewModel.getListMovies(repository)

        setupViewModelAndObserve()
    }

    private fun setupViewModelAndObserve() {
        val daysObserver = Observer<List<Movie?>> {
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
                binding.progress.playAnimation()
                binding.progress.show()
            }

            binding.search.setOnCloseListener {
                binding.emptyState.hide()
                binding.emptyStateFilter.hide()
                false
            }

            binding.progress.cancelAnimation()
            binding.progress.hide()
            binding.recyclerview.show()

            for (movie in it) {
                movie?.let {
                    movie.origen = POPULAR
                    lifecycleScope.launch {
                        repository.insertMovie(movie)
                    }
                }
            }

            setAdapter(it)
        }

        val searchObserver = Observer<List<Movie?>> {
            binding.emptyState.hide()
            binding.emptyStateFilter.hide()
            binding.progress.cancelAnimation()
            binding.progress.hide()
            binding.recyclerview.show()

            binding.search.setOnCloseListener {
                lifecycleScope.launch {
                    setAdapter(repository.getMovieByOrigin(POPULAR))
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

    private fun setAdapter(movies: List<Movie?>) {

        viewAdapter = MovieAdapter(movies) { itemClick(it) }

        viewManager = GridLayoutManager(this.requireContext(), 2)

        binding.recyclerview.apply {
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
        val options = ActivityOptions.makeSceneTransitionAnimation(
            activity,
            item.imageView,
            "main_poster"
        )
        startActivity(intent, options.toBundle())
    }
}