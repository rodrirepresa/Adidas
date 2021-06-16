package com.represa.adidas.data.network.model

import com.represa.adidas.data.database.entities.ReviewEntity

data class Review(
    val id: String,
    val name: String,
    val description: String,
    val imgUrl: String
)

fun Review.toDomainModel() : ReviewEntity {
    return ReviewEntity(
        id = id,
        name = name,
        description = description,
        imgUrl = imgUrl
    )
}