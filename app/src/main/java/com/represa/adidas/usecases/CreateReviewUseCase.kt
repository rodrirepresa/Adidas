package com.represa.adidas.usecases

import com.represa.adidas.data.Repository
import com.represa.adidas.data.network.model.Review

class CreateReviewUseCase(
    private val repository: Repository
) : UseCase<Review, Unit> {

    override suspend fun invoke(review: Review) {
        repository.createReview(review)
    }
}