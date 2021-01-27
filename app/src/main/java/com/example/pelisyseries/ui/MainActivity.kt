package com.example.pelisyseries.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pelisyseries.R

/**
 * Activity principal de nuestra aplicación
 * @author Axel Sanchez
 */
class MainActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}