package com.represa.adidas.data.network.client

import com.represa.adidas.data.network.model.Review
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface ReviewApiService {

    @Headers("Content-Type: application/json")
    @GET("review/{id}")
    suspend fun getReviews(@Path("id") productId: String): List<Review>

    @Headers("Content-Type: application/json")
    @POST("review/{id}")
    suspend fun sendReview(@Path("id") productId: String)
}