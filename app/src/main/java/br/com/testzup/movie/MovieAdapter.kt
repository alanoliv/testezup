package br.com.testzup.movie

import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import br.com.testzup.R
import br.com.testzup.model.Movie
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_movie.view.*

class MovieAdapter (private var movies: List<Movie>?,
                    private val listener: (Int) -> Unit) : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movies?.size ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = movies?.get(position)
        holder.bind(product, position, listener)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val constraintLayoutContent: ConstraintLayout = itemView.constraintLayoutContentMovie
        private val imageViewMovie : ImageView = itemView.imageViewMovie
        private val textViewTitle : TextView = itemView.textViewTitle
        private val textViewYear : TextView = itemView.textViewYear

        fun bind(movie: Movie?, position: Int, listener: (Int) -> Unit) {
            movie?.apply {
                Picasso.get()
                    .load(movie.poster)
                    .into(imageViewMovie)
            }
            textViewTitle.text = movie?.title
            textViewYear.text = movie?.year

            constraintLayoutContent.setOnClickListener {
                listener(position)
            }
        }
    }
}