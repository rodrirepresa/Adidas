package com.represa.adidas.data

import com.represa.adidas.data.database.AppDatabase
import com.represa.adidas.data.database.entities.ProductEntity
import com.represa.adidas.data.database.entities.ReviewEntity
import com.represa.adidas.data.network.client.ProductApiService
import com.represa.adidas.data.network.client.ReviewApiService
import com.represa.adidas.data.network.model.Review
import com.represa.adidas.data.network.model.toDomainModel
import kotlinx.coroutines.flow.Flow

interface Repository {

    fun getProducts(): Flow<List<ProductEntity>>
    fun getProductsFiltered(key: String): Flow<List<ProductEntity>>
    fun getProduct(id: String): ProductEntity
    fun getReviews(id: String): Flow<List<ReviewEntity>>
    suspend fun createReview(review: Review)
    suspend fun fetchProducts()
    suspend fun fetchReviews(productId: String)

}

class RepositoryImpl(
    private val productApiService: ProductApiService,
    private val reviewApiService: ReviewApiService,
    private val appDatabase: AppDatabase
) : Repository {

    override fun getProducts(): Flow<List<ProductEntity>> {
        return appDatabase.productDatabase.getProducts()
    }

    override fun getProductsFiltered(key: String): Flow<List<ProductEntity>> {
        return appDatabase.productDatabase.getProductsFiltered(key)
    }

    override fun getProduct(id: String): ProductEntity {
        return appDatabase.productDatabase.getProduct(id)
    }

    override fun getReviews(id: String): Flow<List<ReviewEntity>> {
        return appDatabase.productDatabase.getReviews(id)
    }

    override suspend fun createReview(review: Review) {
        kotlin.runCatching {
            reviewApiService.sendReview(review.productId, review)
        }.onSuccess {
            appDatabase.productDatabase.insertReview(review.toDomainModel())
        }
    }

    override suspend fun fetchProducts() {
        kotlin.runCatching {
            productApiService.getProducts()
        }.onSuccess { products ->
            appDatabase.productDatabase.insertProducts(
                products.map {
                    it.toDomainModel()
                }
            )
        }
    }

    override suspend fun fetchReviews(productId: String) {
        kotlin.runCatching {
            reviewApiService.getReviews(productId)
        }.onSuccess { reviews ->
            appDatabase.productDatabase.insertReviews(
                reviews.map {
                    it.toDomainModel()
                }
            )
        }
    }

}