package com.represa.adidas.data

import com.represa.adidas.data.network.client.ProductApiService
import com.represa.adidas.data.network.model.Product

interface Repository {

    suspend fun getProducts() : List<Product>

}

class RepositoryImpl(private val productApiService: ProductApiService) : Repository {

    override suspend fun getProducts() : List<Product>{
        return productApiService.getProducts()
    }

}