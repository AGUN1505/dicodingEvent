package com.dicoding.dicodingevent.data.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.dicodingevent.R
import com.dicoding.dicodingevent.data.local.entity.EventEntity
import com.dicoding.dicodingevent.databinding.ItemRowFavoriteBinding

class FavoriteAdapter(
    private val onItemClick: (EventEntity) -> Unit,
    private val onFavoriteClick: (EventEntity) -> Unit
) : ListAdapter<EventEntity, FavoriteAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ItemRowFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val event = getItem(position)
        holder.bind(event)
        holder.itemView.setOnClickListener { onItemClick(event) }
        holder.binding.ivFavoriteIcon.setOnClickListener { onFavoriteClick(event) }
    }

    class MyViewHolder(val binding: ItemRowFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(event: EventEntity) {
            binding.tvItemNameFavorite.text = event.name
            Glide.with(itemView.context)
                .load(event.imageLogo)
                .into(binding.imgItemPhotoFavorite)
            binding.ivFavoriteIcon.setImageResource(R.drawable.baseline_favorite_24)
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<EventEntity>() {
            override fun areItemsTheSame(
                oldItem: EventEntity,
                newItem: EventEntity
            ): Boolean {
                return oldItem.id == newItem.id
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(
                oldItem: EventEntity,
                newItem: EventEntity
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
