package com.aherrera.tmdb.ui.discover

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aherrera.tmdb.R
import com.aherrera.tmdb.data.models.Movie
import com.aherrera.tmdb.databinding.ItemMovieBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.github.florent37.glidepalette.BitmapPalette
import com.github.florent37.glidepalette.GlidePalette

class MoviesDiscoverAdapter (private val listener: ItemListener) : RecyclerView.Adapter<MovieViewHolder>() {

    interface ItemListener {
        //TODO Crear enum para type: movie, tvShow
        fun onItemClicked(movieId: Int, type:String)
    }

    private val items = ArrayList<Movie>()

    fun setItems(items: ArrayList<Movie>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding: ItemMovieBinding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding, listener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) = holder.bind(items[position])
}

class MovieViewHolder(private val itemBinding: ItemMovieBinding, private val listener: MoviesDiscoverAdapter.ItemListener) : RecyclerView.ViewHolder(itemBinding.root),
    View.OnClickListener {

    private lateinit var movie: Movie

    init {
        itemBinding.root.setOnClickListener(this)
    }

    @SuppressLint("SetTextI18n")
    fun bind(item: Movie) {
        this.movie = item

        itemBinding.movieTitle.text = item.original_title

        Glide.with(itemBinding.root)
            .load(item.getPosterUrl())
            .listener(GlidePalette.with(item.getPosterUrl())
                .use(BitmapPalette.Profile.VIBRANT)
                .intoBackground(itemBinding.movieTittleBackground)
                .crossfade(true))
            .placeholder(R.drawable.ic_local_play)
            .error(R.drawable.ic_local_play)
            .fitCenter()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(itemBinding.movieImage)
    }

    override fun onClick(v: View?) {
        listener.onItemClicked(movieId = movie.id, type = "movie")
    }
}

