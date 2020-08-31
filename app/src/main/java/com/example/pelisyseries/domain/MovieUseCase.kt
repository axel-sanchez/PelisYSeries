package com.example.pelisyseries.domain

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.pelisyseries.ui.PopularFragment
import com.example.pelisyseries.ui.TopRatedFragment
import com.example.pelisyseries.ui.UpcomingFragment
import com.example.pelisyseries.ui.adapter.ItemViewPager
import java.util.*

/**
 * Caso de uso para las movies
 * @author Axel Sanchez
 */
class MovieUseCase {
    /**
     * Recibe el mutableLiveData y obtiene su listado de movies
     * @return devuelve un listado de movies
     */
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun getMovieList(): MutableList<ItemViewPager> {
        val listado: MutableList<ItemViewPager> = LinkedList()
        listado.add(ItemViewPager("Popular", PopularFragment()))
        listado.add(ItemViewPager("Top Rated", TopRatedFragment()))
        listado.add(ItemViewPager("Upcoming", UpcomingFragment()))
        return listado
    }
}
