package com.example.pelisyseries.ui

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.pelisyseries.databinding.FragmentMainBinding
import com.example.pelisyseries.domain.MainUseCase
import com.example.pelisyseries.ui.adapter.ItemViewPager
import com.example.pelisyseries.ui.adapter.ViewPageAdapter
import com.example.pelisyseries.viewmodel.MainViewModel
import org.koin.android.ext.android.inject

/**
 * Primer fragment en mostrarse en el activity principal
 * @author Axel Sanchez
 */
class MainFragment: Fragment() {

    private val mainUseCase: MainUseCase by inject()

    private val viewModel: MainViewModel by viewModels(
        factoryProducer = { MainViewModel.MovieViewModelFactory(mainUseCase) }
    )

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