package com.represa.adidas.di

import com.represa.adidas.data.Repository
import com.represa.adidas.data.RepositoryImpl
import com.represa.adidas.data.network.client.ProductApiService
import com.represa.adidas.data.network.client.ReviewApiService
import com.represa.adidas.ui.viewmodels.ProductViewModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

private const val BASE_PRODUCT_URL_NAME = "BASE_PRODUCT_URL"
private const val BASE_PRODUCT_URL = "http://localhost:3001"

private const val BASE_REVIEW_URL_NAME = "BASE_REVIEW_URL"
private const val BASE_REVIEW_URL = "http://localhost:3002"

val appModule = module {

    single {
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    single(named(BASE_PRODUCT_URL_NAME)) {
        BASE_PRODUCT_URL
    }

    single(named(BASE_REVIEW_URL_NAME)) {
        BASE_REVIEW_URL
    }

    single(named("ProductBuilder")) {
        Retrofit.Builder()
            .baseUrl(get<String>(named(BASE_PRODUCT_URL_NAME)))
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .build()
    }

    single(named("ReviewBuilder")) {
        Retrofit.Builder()
            .baseUrl(get<String>(named(BASE_REVIEW_URL_NAME)))
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .build()
    }

    single {
        get<Retrofit>(named("ProductBuilder")).create(ProductApiService::class.java)
    }

    single {
        get<Retrofit>(named("ReviewBuilder")).create(ReviewApiService::class.java)
    }

    single<Repository> {
        RepositoryImpl(get())
    }

    viewModel { ProductViewModel(get()) }

}