package com.example.pelisyseries.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.pelisyseries.R
import com.example.pelisyseries.data.models.Movie
import com.squareup.picasso.Picasso

const val BASE_URL_IMAGEN = "https://image.tmdb.org/t/p/w500"
/**
 * Clase que adapta el recyclerview de [MainFragment]
 * @author Axel Sanchez
 */
class MovieAdapter(
    var mItems: List<Movie>,
    var itemClick: (Movie) -> Unit
) : RecyclerView.Adapter<MovieAdapter.ViewHolder>(), Filterable {

    private var mFilteredList = mItems

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: Movie, itemClick: (Movie) -> Unit) {
            var image = itemView.findViewById<ImageView>(R.id.image)
            var title = itemView.findViewById<TextView>(R.id.title)

            ViewCompat.setTransitionName(image, item.title)

            Picasso.with(itemView.context)
                .load("$BASE_URL_IMAGEN${item.poster_path}")
                .into(image)

            title.text = item.title

            image.setOnClickListener {
                item.imageView = image
                itemClick(item)
            }
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_movie,
                parent,
                false
            )
        )
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(
        mFilteredList[position],
        itemClick
    )

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = mFilteredList.size

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {

                val charString = charSequence.toString()

                if (charString.isEmpty()) {

                    mFilteredList = mItems
                } else {

                    val filteredList: MutableList<Movie> = mutableListOf()

                    for (item in mItems) {

                        /*if (item.text.toLowerCase().contains(charString.toLowerCase())) {

                            filteredList.add(item)
                        }*/
                    }
                    mFilteredList = filteredList
                }

                val filterResults = FilterResults()
                filterResults.values = mFilteredList
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                mFilteredList = filterResults.values as List<Movie>
                notifyDataSetChanged()
            }
        }
    }

    fun setItems(newItems: List<Movie>) {
        mItems = newItems
    }
}