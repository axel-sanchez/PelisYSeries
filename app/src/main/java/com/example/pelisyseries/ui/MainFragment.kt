package com.example.pelisyseries.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pelisyseries.R
import com.example.pelisyseries.ui.customs.BaseFragment

/**
 * Primer fragment en mostrarse en el activity principal
 * @author Axel Sanchez
 */
class MainFragment: BaseFragment() {
    override fun onBackPressFragment() = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}