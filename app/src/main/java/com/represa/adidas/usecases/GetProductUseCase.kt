package com.represa.adidas.usecases

import com.represa.adidas.data.Repository
import com.represa.adidas.data.database.entities.ProductEntity

class GetProductUseCase(
    private val repository: Repository
) : UseCase<String, ProductEntity> {

    override suspend fun invoke(productId: String): ProductEntity {
        return repository.getProduct(productId)
    }
}