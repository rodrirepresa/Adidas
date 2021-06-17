package com.represa.adidas.usecases

import com.represa.adidas.data.Repository
import com.represa.adidas.data.database.entities.ReviewEntity
import com.represa.adidas.util.SynchronousUseCase
import kotlinx.coroutines.flow.Flow

class GetReviewsUseCase(
    private val repository: Repository
) : SynchronousUseCase<String, Flow<List<ReviewEntity>>> {

    override fun invoke(productId: String): Flow<List<ReviewEntity>> {
        return repository.getReviews(productId)
    }
}