package com.represa.adidas.data.network.model

import com.represa.adidas.data.database.entities.ReviewEntity

data class Review(
    val productId: String,
    val locale: String = "EN",
    val rating: Int,
    val text: String
)

fun Review.toDomainModel() : ReviewEntity {
    return ReviewEntity(
        productId = productId,
        locale = locale,
        rating = rating,
        text = text[0] + text.subSequence(1, text.length).toString(),
        id = 0
    )
}