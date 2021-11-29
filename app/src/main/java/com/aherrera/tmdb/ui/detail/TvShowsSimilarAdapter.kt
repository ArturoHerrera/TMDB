package com.aherrera.tmdb.ui.detail

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aherrera.tmdb.R
import com.aherrera.tmdb.data.models.SimilarTvShow
import com.aherrera.tmdb.databinding.ItemMovieBinding
import com.aherrera.tmdb.ui.discover.MoviesDiscoverAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.github.florent37.glidepalette.BitmapPalette
import com.github.florent37.glidepalette.GlidePalette

class TvShowsSimilarAdapter (private val listener: MoviesDiscoverAdapter.ItemListener) : RecyclerView.Adapter<SimilarTvShowViewHolder>() {

    private val items = ArrayList<SimilarTvShow>()

    fun setItems(items: ArrayList<SimilarTvShow>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimilarTvShowViewHolder {
        val binding: ItemMovieBinding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SimilarTvShowViewHolder(binding, listener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: SimilarTvShowViewHolder, position: Int) = holder.bind(items[position])
}

class SimilarTvShowViewHolder(private val itemBinding: ItemMovieBinding, private val listener: MoviesDiscoverAdapter.ItemListener) : RecyclerView.ViewHolder(itemBinding.root),
    View.OnClickListener {

    private lateinit var movie: SimilarTvShow

    init {
        itemBinding.root.setOnClickListener(this)
    }

    @SuppressLint("SetTextI18n")
    fun bind(item: SimilarTvShow) {
        this.movie = item

        itemBinding.movieTitle.text = item.original_name

        Glide.with(itemBinding.root)
            .load(item.getPosterUrl())
            .listener(
                GlidePalette.with(item.getPosterUrl())
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
        listener.onItemClicked(movieId = movie.id, type = "tvShow")
    }
}
