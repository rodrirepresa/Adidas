package com.represa.adidas.usecases

import com.represa.adidas.data.Repository
import com.represa.adidas.data.database.entities.ProductEntity
import com.represa.adidas.util.SynchronousUseCase
import com.represa.adidas.util.UseCase
import kotlinx.coroutines.flow.Flow

class GetProductsUseCase(
    private val repository: Repository
) : UseCase<Unit, List<ProductEntity>> {

    override suspend fun invoke(param: Unit): List<ProductEntity> {
        return repository.getProducts()
    }
}