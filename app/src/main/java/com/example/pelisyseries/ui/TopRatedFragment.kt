package com.example.pelisyseries.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.example.pelisyseries.R
import com.example.pelisyseries.data.TableMovie
import com.example.pelisyseries.data.models.Movie
import com.example.pelisyseries.data.repository.GenericRepository
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

/**
 * Primer fragment en mostrarse en el activity principal
 * @author Axel Sanchez
 */
class TopRatedFragment: BaseFragment() {

    private lateinit var repository: GenericRepository

    private val viewModel: TopRatedViewModel by lazy { ViewModelProviders.of(requireActivity(), TopRatedViewModelFactory(TopRatedUseCase())).get(TopRatedViewModel::class.java) }

    private lateinit var viewAdapter: MovieAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    private lateinit var progress: LottieAnimationView
    private lateinit var recyclerview: RecyclerView

    override fun onBackPressFragment() = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        repository = GenericRepository.getInstance(context!!)

        progress = view.findViewById(R.id.progress)
        recyclerview = view.findViewById(R.id.recyclerview)

        CoroutineScope(Main).launch {
            viewModel.getListMovies(repository)
        }

        setupViewModelAndObserve()
    }

    /**
     * Configuramos el viewModel para estar a la escucha de nuestra petición a la api de peliculas
     */
    private fun setupViewModelAndObserve() {
        val daysObserver = Observer<List<Movie>> {
            //Actualizar la vista

            progress.cancelAnimation()
            progress.visibility = View.GONE
            recyclerview.visibility = View.VISIBLE

            for(movie in it){
                movie.origen = TOP_RATED
                repository.insert(movie)
            }

            setAdapter(it)
        }
        viewModel.getListMoviesLiveData().observe(viewLifecycleOwner, daysObserver)
    }

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

    private fun itemClick(item: Movie){
        Toast.makeText(context, "Presionó ${item.original_title}", Toast.LENGTH_SHORT).show()
    }
}