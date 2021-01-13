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
import com.example.pelisyseries.databinding.FragmentMainBinding
import com.example.pelisyseries.domain.MovieUseCase
import com.example.pelisyseries.ui.adapter.ItemViewPager
import com.example.pelisyseries.ui.adapter.ViewPageAdapter
import com.example.pelisyseries.viewmodel.MovieViewModel

/**
 * Primer fragment en mostrarse en el activity principal
 * @author Axel Sanchez
 */
class MainFragment: Fragment() {

    private val viewModel: MovieViewModel by lazy {
        ViewModelProviders.of(requireActivity(), MovieViewModel.MovieViewModelFactory(MovieUseCase())).get(MovieViewModel::class.java)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getListFragments()

        copyFragment = this

        setupViewModelAndObserve()
    }

    private var fragmentMainBinding: FragmentMainBinding? = null
    private val binding get() = fragmentMainBinding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentMainBinding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentMainBinding = null
    }

    private fun setupViewModelAndObserve() {
        val daysObserver = Observer<MutableList<ItemViewPager>> {
            setAdapter(it)
        }
        viewModel.getListMoviesLiveData().observe(this, daysObserver)
    }

    private fun setAdapter(list: MutableList<ItemViewPager>) {
        val adapter = ViewPageAdapter(childFragmentManager, list)
        binding.viewpager.adapter = adapter
        binding.tabs.setupWithViewPager(binding.viewpager)
    }

    companion object{
        /**
         * Utilizo esta variable para crear el viewModel de [DetailsActivity]
         * El activity de Youtube no me dejaba usar this
         */
        lateinit var copyFragment: Fragment
    }
}