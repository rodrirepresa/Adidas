package com.represa.adidas.usecases

import com.represa.adidas.data.Repository
import com.represa.adidas.data.database.entities.ReviewEntity
import com.represa.adidas.util.UseCase

class CreateReviewUseCase(
    private val repository: Repository
) : UseCase<ReviewEntity, Unit> {

    override suspend fun invoke(review: ReviewEntity) {
        repository.createReview(review)
    }
}