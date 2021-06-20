package com.represa.adidas.usecases

import com.represa.adidas.data.Repository
import com.represa.adidas.util.UseCase

class FetchProductsUseCase(
    private val repository: Repository
) : UseCase<Unit, Unit> {

    override suspend fun invoke(param: Unit) {
        repository.fetchProducts()
    }
}