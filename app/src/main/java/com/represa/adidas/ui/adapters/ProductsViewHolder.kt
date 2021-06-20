package com.represa.adidas.ui.adapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.represa.adidas.data.database.entities.ProductEntity
import com.represa.adidas.databinding.ItemBigProductListBinding
import com.represa.adidas.databinding.ItemProductListBinding

sealed class ProductsViewHolder(
    private val view: View
) : RecyclerView.ViewHolder(view) {

    abstract fun bind(item: ProductEntity)

    class ProductVH(
        view: View,
        private val onClickProduct: (String) -> Unit,
    ) : ProductsViewHolder(view) {

        constructor(
            binding: ItemProductListBinding,
            onClickProduct: (String) -> Unit
        ) : this(binding.root, onClickProduct) {
            productImage = binding.productImage
            productName = binding.productName
            productPrice = binding.productPrice
            frameLayout = binding.frameLayout
        }

        constructor(
            binding: ItemBigProductListBinding,
            onClickProduct: (String) -> Unit,
        ) : this(binding.root, onClickProduct) {
            productImage = binding.productImage
            productName = binding.productName
            productPrice = binding.productPrice
            frameLayout = binding.frameLayout
        }

        lateinit var productImage: ImageView
        lateinit var productName: TextView
        lateinit var productPrice: TextView
        lateinit var frameLayout: ConstraintLayout

        override fun bind(product: ProductEntity) {
            productImage.load(product.imgUrl)
            productName.text = product.name
            productPrice.text = product.price.toString() + product.currency
            frameLayout.setOnClickListener { onClickProduct.invoke(product.id) }
        }
    }
}