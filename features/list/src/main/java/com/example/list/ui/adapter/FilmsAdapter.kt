package com.example.list.ui.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.films.domain.entity.FilmFromPopular
import com.example.list.ui.adapter.viewholder.FilmsViewHolder
import com.example.list.ui.adapter.viewholder.HiddenFilmsViewHolder

class FilmsAdapter(
    private val onItemClicked: (FilmFromPopular) -> Unit,
    private val onItemLongClicked: (FilmFromPopular) -> Unit,
) : PagingDataAdapter<FilmFromPopular, RecyclerView.ViewHolder>(FilmComparator) {

    override fun getItemViewType(position: Int): Int = if (getItem(position)?.visibility == true) {
        NORMAL_VIEW_TYPE
    } else {
        DELETED_VIEW_TYPE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            NORMAL_VIEW_TYPE -> {
                FilmsViewHolder.from(parent)
            }

            DELETED_VIEW_TYPE -> {
                HiddenFilmsViewHolder.from(parent)
            }

            else -> throw java.lang.Exception("Invalid view holder type")
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is FilmsViewHolder) {
            holder.bind(
                item = getItem(position),
                onItemClicked = onItemClicked,
                onItemLongClicked = onItemLongClicked,
            )
        }
    }

    companion object {
        private const val NORMAL_VIEW_TYPE = 1
        private const val DELETED_VIEW_TYPE = 2
    }
}

private object FilmComparator : DiffUtil.ItemCallback<FilmFromPopular>() {
    override fun areItemsTheSame(oldItem: FilmFromPopular, newItem: FilmFromPopular): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: FilmFromPopular, newItem: FilmFromPopular): Boolean =
        oldItem == newItem
}
