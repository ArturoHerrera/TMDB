package com.aherrera.tmdb.ui.discover

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aherrera.tmdb.R
import com.aherrera.tmdb.data.models.TvShow
import com.aherrera.tmdb.databinding.ItemMovieBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.github.florent37.glidepalette.BitmapPalette
import com.github.florent37.glidepalette.GlidePalette

class TvShowDiscoverAdapter (private val listener: MoviesDiscoverAdapter.ItemListener) : RecyclerView.Adapter<TvShowViewHolder>() {

    private val items = ArrayList<TvShow>()

    fun setItems(items: ArrayList<TvShow>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowViewHolder {
        val binding: ItemMovieBinding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TvShowViewHolder(binding, listener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: TvShowViewHolder, position: Int) = holder.bind(items[position])
}

class TvShowViewHolder(private val itemBinding: ItemMovieBinding, private val listener: MoviesDiscoverAdapter.ItemListener) : RecyclerView.ViewHolder(itemBinding.root),
    View.OnClickListener {

    private lateinit var tvShow: TvShow

    init {
        itemBinding.root.setOnClickListener(this)
    }

    @SuppressLint("SetTextI18n")
    fun bind(item: TvShow) {
        this.tvShow = item

        itemBinding.movieTitle.text = item.name

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
        listener.onItemClicked(movieId = tvShow.id, type ="tvShow")
    }
}

