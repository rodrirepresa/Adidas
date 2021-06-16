package com.represa.adidas.data.network.model


data class Product(
    val currency: String,
    val price: Int,
    val id: String,
    val name: String,
    val description: String,
    val imgUrl: String
)