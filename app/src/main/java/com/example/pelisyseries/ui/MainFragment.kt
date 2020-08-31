package com.example.pelisyseries.ui

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.example.pelisyseries.R
import com.example.pelisyseries.data.models.Movie
import com.example.pelisyseries.domain.MovieUseCase
import com.example.pelisyseries.ui.adapter.ItemViewPager
import com.example.pelisyseries.ui.adapter.MovieAdapter
import com.example.pelisyseries.ui.adapter.ViewPageAdapter
import com.example.pelisyseries.ui.customs.BaseFragment
import com.example.pelisyseries.viewmodel.MovieViewModel
import com.example.pelisyseries.viewmodel.MovieViewModelFactory
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch

/**
 * Primer fragment en mostrarse en el activity principal
 * @author Axel Sanchez
 */
class MainFragment: BaseFragment() {

    private val viewModel: MovieViewModel by lazy { ViewModelProviders.of(requireActivity(), MovieViewModelFactory(MovieUseCase())).get(MovieViewModel::class.java) }

    override fun onBackPressFragment() = false

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getListFragments()

        copyFragment = this

        setupViewModelAndObserve()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    /**
     * Configuramos el [MovieViewModel] para estar a la escucha de nuestro listado de Fragments
     */
    private fun setupViewModelAndObserve() {
        val daysObserver = Observer<MutableList<ItemViewPager>> {
            //Actualizar la vista
            setAdapter(it)
        }
        viewModel.getListMoviesLiveData().observe(this, daysObserver)
    }

    /**
     * Adaptamos el viewPager con los 3 fragments que contienen los listados de peliculas
     * @param [listado] un mutableLiveData que contiene los [ItemViewPager]
     */
    private fun setAdapter(listado: MutableList<ItemViewPager>) {
        val adapter = ViewPageAdapter(childFragmentManager, listado)
        viewpager.adapter = adapter
        tabs.setupWithViewPager(viewpager)
    }

    companion object{
        /**
         * Utilizo esta variable para crear el viewModel de [DetailsActivity]
         * El activity de Youtube no me dejaba usar this
         */
        lateinit var copyFragment: Fragment
    }
}