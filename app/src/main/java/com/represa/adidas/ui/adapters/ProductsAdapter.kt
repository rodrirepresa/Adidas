package com.represa.adidas.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.represa.adidas.data.database.entities.ProductEntity
import com.represa.adidas.databinding.ItemProductListBinding

class ProductsAdapter(val onProductClick: (String) -> Unit) : ListAdapter<ProductEntity, ProductsAdapter.ViewHolder>(PhotoDiffCallback()) {


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position)!!, onProductClick)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }


    class ViewHolder private constructor(private val binding: ItemProductListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ProductEntity, onProductClick: (String) -> Unit) {
            binding.productImage.load(item.imgUrl)
            binding.frameLayout.setOnClickListener{
                onProductClick(item.id)
            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemProductListBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }
}

class PhotoDiffCallback : DiffUtil.ItemCallback<ProductEntity>() {
    override fun areItemsTheSame(oldItem: ProductEntity, newItem: ProductEntity): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ProductEntity, newItem: ProductEntity): Boolean {
        return oldItem.id == newItem.id
    }
}