package com.represa.adidas.data

import com.represa.adidas.data.database.AppDatabase
import com.represa.adidas.data.database.entities.ProductEntity
import com.represa.adidas.data.network.client.ProductApiService
import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun getProducts() : Flow<List<ProductEntity>>

}

class RepositoryImpl(private val productApiService: ProductApiService, private val appDatabase: AppDatabase) : Repository {

    override suspend fun getProducts() : Flow<List<ProductEntity>> {
        return appDatabase.productDatabase.getProducts()
    }

}