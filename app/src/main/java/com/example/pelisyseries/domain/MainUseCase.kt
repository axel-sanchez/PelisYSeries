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
class MainUseCase {
    /**
     * Recibe el mutableLiveData y obtiene su listado de movies para crear los [ItemViewPager]
     * @return devuelve un listado de [ItemViewPager]
     */
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun getMovieList(): MutableList<ItemViewPager> {
        val list: MutableList<ItemViewPager> = LinkedList()
        list.add(ItemViewPager("Popular", PopularFragment()))
        list.add(ItemViewPager("Top Rated", TopRatedFragment()))
        list.add(ItemViewPager("Upcoming", UpcomingFragment()))
        return list
    }
}