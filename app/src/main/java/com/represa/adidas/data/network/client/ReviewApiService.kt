package com.represa.adidas.data.network.client

import com.represa.adidas.data.network.model.Review
import retrofit2.http.*

interface ReviewApiService {

    @Headers("Content-Type: application/json")
    @GET("reviews/{id}")
    suspend fun getReviews(@Path("id") productId: String): List<Review>

    @Headers("Content-Type: application/json")
    @POST("reviews/{id}")
    suspend fun sendReview(@Path("id") productId: String, @Body review: Review)
}