package com.represa.adidas.usecases

import com.represa.adidas.data.Repository
import com.represa.adidas.data.database.entities.ProductEntity
import com.represa.adidas.util.SynchronousUseCase
import com.represa.adidas.util.UseCase
import kotlinx.coroutines.flow.Flow

class FetchReviewsUseCase(
    private val repository: Repository
) : UseCase<String, Unit> {

    override suspend fun invoke(productId: String) {
        repository.fetchReviews(productId)
    }
}