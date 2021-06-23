package com.represa.adidas.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.represa.adidas.data.database.entities.ReviewEntity
import com.represa.adidas.databinding.ItemReviewBinding

class ReviewsAdapter : ListAdapter<ReviewEntity,
        ReviewsAdapter.ViewHolder>(ReviewDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(private val binding: ItemReviewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ReviewEntity) {
            binding.rating.text = item.rating.toString()
            binding.reviewText.text = item.text
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemReviewBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }
}

class ReviewDiffCallback : DiffUtil.ItemCallback<ReviewEntity>() {
    override fun areItemsTheSame(oldItem: ReviewEntity, newItem: ReviewEntity): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ReviewEntity, newItem: ReviewEntity): Boolean {
        return oldItem.id == newItem.id
    }
}