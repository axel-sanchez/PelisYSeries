package com.example.pelisyseries.ui

import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.example.pelisyseries.R
import com.example.pelisyseries.data.models.Movie
import com.example.pelisyseries.data.repository.GenericRepository
import com.example.pelisyseries.data.repository.POPULAR
import com.example.pelisyseries.domain.PopularUseCase
import com.example.pelisyseries.ui.adapter.MovieAdapter
import com.example.pelisyseries.ui.customs.BaseFragment
import com.example.pelisyseries.viewmodel.PopularViewModel
import com.example.pelisyseries.viewmodel.PopularViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch


@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
/**
 * Fragment que muestra el primer listado de movies en el viewPager
 * @author Axel Sanchez
 */
class PopularFragment: BaseFragment() {

    private lateinit var repository: GenericRepository

    private val viewModel: PopularViewModel by lazy { ViewModelProviders.of(
        requireActivity(), PopularViewModelFactory(
            PopularUseCase()
        )
    ).get(PopularViewModel::class.java) }

    private lateinit var viewAdapter: MovieAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    private lateinit var progress: LottieAnimationView
    private lateinit var recyclerview: RecyclerView
    private lateinit var searchView: SearchView

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

        repository = GenericRepository.getInstance(context!!)

        progress = view.findViewById(R.id.progress)
        recyclerview = view.findViewById(R.id.recyclerview)
        searchView = view.findViewById(R.id.search)

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

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    viewAdapter.filter.filter(newText)
                    return false
                }
            })

            progress.cancelAnimation()
            progress.visibility = View.GONE
            recyclerview.visibility = View.VISIBLE

            for(movie in it){
                movie.origen = POPULAR
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