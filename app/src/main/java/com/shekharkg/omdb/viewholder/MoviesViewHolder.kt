package com.shekharkg.omdb.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.shekharkg.omdb.dao.Movie
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_movie.view.*
import java.lang.Exception

/**
 * Created by shekhar on 2019-09-07.
 */
class MoviesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

  fun bind(movie: Movie) {

    itemView.progressBarSmall.visibility = View.VISIBLE

    Picasso.get().load(movie.Poster).into(itemView.posterIV, object : Callback {
      override fun onSuccess() {
        itemView.progressBarSmall.visibility = View.GONE
      }

      override fun onError(e: Exception?) {
        itemView.progressBarSmall.visibility = View.GONE
      }

    })
    itemView.movieTitleTV.text = movie.Title
    itemView.movieYearTV.text = movie.Year

  }

}