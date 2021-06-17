package com.represa.adidas.data.network.model

import com.represa.adidas.data.database.entities.ReviewEntity

data class Review(
    val productId: String,
    val name: String,
    val description: String,
    val imgUrl: String
)

fun Review.toDomainModel() : ReviewEntity {
    return ReviewEntity(
        productId = productId,
        locale = name,
        rating = description,
        text = imgUrl
    )
}