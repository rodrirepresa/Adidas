package com.represa.adidas.data.network.model

import com.represa.adidas.data.database.entities.ProductEntity


data class Product(
    val currency: String,
    val price: Int,
    val id: String,
    val name: String,
    val description: String,
    val imgUrl: String
)

fun Product.toDomainModel() : ProductEntity {
    return ProductEntity(
        currency = currency,
        price = price,
        id = id,
        name = name[0].toUpperCase() + name.subSequence(1,name.length).toString(),
        description = description,
        imgUrl = imgUrl
    )
}