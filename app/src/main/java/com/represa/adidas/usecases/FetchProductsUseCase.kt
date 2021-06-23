package com.represa.adidas.usecases

import com.represa.adidas.data.Repository

class FetchProductsUseCase(
    private val repository: Repository
) : UseCase<Unit, Unit> {

    override suspend fun invoke(param: Unit) {
        repository.fetchProducts()
    }
}