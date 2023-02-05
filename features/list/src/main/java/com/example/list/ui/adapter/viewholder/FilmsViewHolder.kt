package com.example.list.ui.adapter.viewholder

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.request.CachePolicy
import coil.transform.RoundedCornersTransformation
import com.example.films.domain.entity.FilmFromPopular
import com.example.list.R
import com.example.list.databinding.ItemFilmsBinding

class FilmsViewHolder(
    private val binding: ItemFilmsBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        item: FilmFromPopular?,
        onItemClicked: (FilmFromPopular) -> Unit,
        onItemLongClicked: (FilmFromPopular) -> Unit,
    ) {
        with(binding) {
            item?.let {
                ivLogo.load(item.posterUrlPreview) {
                    memoryCachePolicy(CachePolicy.ENABLED)
                    diskCachePolicy(CachePolicy.ENABLED)
                    transformations(RoundedCornersTransformation(15.0f))
                }
                ivFav.visibility = if (item.isFavorite) {
                    View.VISIBLE
                } else View.INVISIBLE
                tvTitle.text = item.nameRu
                tvSubtitle.text = binding.root.context.getString(
                    R.string.subtitle,
                    item.genres.first().genre.replaceFirstChar { it.uppercaseChar() },
                    item.year
                )
                itemView.setOnClickListener { onItemClicked(item) }
                itemView.setOnLongClickListener {
                    onItemLongClicked(item.apply {
                        isFavorite = !isFavorite
                    })
                    true
                }
            }
        }
    }

    companion object {

        fun from(parent: ViewGroup): FilmsViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemFilmsBinding.inflate(layoutInflater, parent, false)
            return FilmsViewHolder(binding)
        }
    }
}