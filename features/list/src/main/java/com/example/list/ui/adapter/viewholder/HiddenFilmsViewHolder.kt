package com.example.list.ui.adapter.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.list.databinding.ItemHiddenFilmsBinding

class HiddenFilmsViewHolder(
    private val binding: ItemHiddenFilmsBinding,
) : RecyclerView.ViewHolder(binding.root) {

    companion object {

        fun from(parent: ViewGroup): HiddenFilmsViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemHiddenFilmsBinding.inflate(layoutInflater, parent, false)
            return HiddenFilmsViewHolder(binding)
        }
    }
}