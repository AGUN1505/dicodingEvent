package com.dicoding.dicodingevent.data.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.dicodingevent.data.response.ListEventsItem
import com.dicoding.dicodingevent.databinding.ItemRowEventBinding

class UpcomingAdapter(private val onItemClick: (ListEventsItem) -> Unit) :
    ListAdapter<ListEventsItem, UpcomingAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ItemRowEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val review = getItem(position)
        holder.bind(review)
        holder.itemView.setOnClickListener { onItemClick(review) }
    }

    class MyViewHolder(private val binding: ItemRowEventBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(event: ListEventsItem) {
            binding.tvItemName.text = event.name
            Glide.with(itemView.context).load(event.mediaCover).into(binding.imgItemPhoto)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListEventsItem>() {
            override fun areItemsTheSame(
                oldItem: ListEventsItem, newItem: ListEventsItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ListEventsItem, newItem: ListEventsItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

}