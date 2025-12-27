package com.anime.app.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.anime.app.R
import com.anime.app.data.db.AnimeEntity
import com.anime.app.databinding.ItemAnimeBinding
import com.bumptech.glide.Glide

class AnimeAdapter(
    private val onItemClick: (AnimeEntity) -> Unit = {}
) : ListAdapter<AnimeEntity, AnimeAdapter.AnimeViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemAnimeBinding.inflate(inflater, parent, false)
        return AnimeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AnimeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class AnimeViewHolder(
        private val binding: ItemAnimeBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: AnimeEntity) {
            binding.tvTitle.text = item.title
            binding.tvEpisodes.text = "Episodes: ${item.episodes ?: "N/A"}"
            binding.tvRating.text = "Rating: ${item.rating ?: "N/A"}"

            Glide.with(binding.imgPoster.context)
                .load(item.imageUrl)
                .placeholder(R.drawable.ic_launcher_background)
                .into(binding.imgPoster)

            binding.root.setOnClickListener {
                onItemClick(item)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<AnimeEntity>() {
        override fun areItemsTheSame(old: AnimeEntity, new: AnimeEntity): Boolean {
            return old.id == new.id
        }

        override fun areContentsTheSame(old: AnimeEntity, new: AnimeEntity): Boolean {
            return old == new
        }
    }
}
