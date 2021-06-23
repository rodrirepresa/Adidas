package com.represa.adidas.usecases

import com.represa.adidas.data.Repository

class FetchReviewsUseCase(
    private val repository: Repository
) : UseCase<String, Unit> {

    override suspend fun invoke(productId: String) {
        repository.fetchReviews(productId)
    }
}