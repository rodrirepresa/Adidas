package com.represa.adidas.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.represa.adidas.R
import com.represa.adidas.data.database.entities.ProductEntity
import com.represa.adidas.databinding.ItemBigProductListBinding
import com.represa.adidas.databinding.ItemProductListBinding

class ProductsAdapter(private val onProductClick: (String) -> Unit) :
    ListAdapter<ProductEntity, ProductsViewHolder>(ProductDiffCallback()) {

    override fun getItemViewType(position: Int): Int {
        return when (position % 9) {
            0, 1, 3, 4, 5, 6 -> R.layout.item_product_list
            else -> R.layout.item_big_product_list
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            R.layout.item_product_list -> ProductsViewHolder.ProductVH(
                ItemProductListBinding.inflate(inflater, parent, false), onProductClick
            )
            R.layout.item_big_product_list -> ProductsViewHolder.ProductVH(
                ItemBigProductListBinding.inflate(inflater, parent, false), onProductClick
            )
            else -> error("This viewType is not supported")
        }
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}

class ProductDiffCallback : DiffUtil.ItemCallback<ProductEntity>() {
    override fun areItemsTheSame(oldItem: ProductEntity, newItem: ProductEntity): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ProductEntity, newItem: ProductEntity): Boolean {
        return oldItem.id == newItem.id
    }
}