package com.represa.adidas.usecases

import com.represa.adidas.data.Repository
import com.represa.adidas.data.database.entities.ProductEntity

class GetProductsUseCase(
    private val repository: Repository
) : UseCase<Unit, List<ProductEntity>> {

    override suspend fun invoke(param: Unit): List<ProductEntity> {
        return repository.getProducts()
    }
}