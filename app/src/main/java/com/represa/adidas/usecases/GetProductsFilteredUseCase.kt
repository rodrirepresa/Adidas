package com.represa.adidas.usecases

import com.represa.adidas.data.Repository
import com.represa.adidas.data.database.entities.ProductEntity
import com.represa.adidas.util.SynchronousUseCase
import kotlinx.coroutines.flow.Flow

class GetProductsFilteredUseCase(
    private val repository: Repository
) : SynchronousUseCase<String, Flow<List<ProductEntity>>> {

    override fun invoke(key: String): Flow<List<ProductEntity>> {
        return repository.getProductsFiltered(key)
    }
}