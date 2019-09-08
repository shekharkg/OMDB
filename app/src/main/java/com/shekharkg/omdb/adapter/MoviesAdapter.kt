package com.shekharkg.omdb.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shekharkg.omdb.R
import com.shekharkg.omdb.dao.Movie
import com.shekharkg.omdb.viewholder.MoviesViewHolder

/**
 * Created by shekhar on 2019-09-07.
 */
class MoviesAdapter : RecyclerView.Adapter<MoviesViewHolder>() {

  var movies: ArrayList<Movie>? = null

  fun resetAdapter(movies: ArrayList<Movie>) {
    this.movies = movies
  }

  fun clearAdapter() {
    this.movies?.clear()
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
    val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
    return MoviesViewHolder(itemView)
  }

  override fun getItemCount(): Int {
    return movies?.size ?: 0
  }

  override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
    movies?.get(position)?.let { holder.bind(it) }
  }
}