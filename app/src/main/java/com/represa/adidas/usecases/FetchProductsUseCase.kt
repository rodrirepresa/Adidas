package com.represa.adidas.usecases

import com.represa.adidas.data.Repository
import com.represa.adidas.data.database.entities.ProductEntity
import com.represa.adidas.util.SynchronousUseCase
import com.represa.adidas.util.UseCase
import kotlinx.coroutines.flow.Flow

class FetchProductsUseCase(
    private val repository: Repository
) : UseCase<Unit, Unit> {

    override suspend fun invoke(param: Unit) {
        repository.fetchProducts()
    }
}