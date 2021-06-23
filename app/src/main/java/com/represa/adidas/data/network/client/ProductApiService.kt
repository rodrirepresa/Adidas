package com.represa.adidas.data.network.client

import com.represa.adidas.data.network.model.Product
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface ProductApiService {

    @Headers("Content-Type: application/json")
    @GET("/product/{id}")
    suspend fun getProduct(@Path("id") productId: String): Product

    @Headers("Content-Type: application/json")
    @GET("/product")
    suspend fun getProducts(): List<Product>
}