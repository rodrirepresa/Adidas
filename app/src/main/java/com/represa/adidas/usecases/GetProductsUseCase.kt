package com.represa.adidas.usecases

import com.represa.adidas.data.Repository
import com.represa.adidas.data.database.entities.ProductEntity
import com.represa.adidas.util.SynchronousUseCase
import kotlinx.coroutines.flow.Flow

class GetProductsUseCase(
    private val repository: Repository
) : SynchronousUseCase<Unit, Flow<List<ProductEntity>>> {

    override fun invoke(param: Unit): Flow<List<ProductEntity>> {
        return repository.getProducts()
    }
}