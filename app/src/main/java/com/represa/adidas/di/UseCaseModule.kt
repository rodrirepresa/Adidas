package com.represa.adidas.di

import com.represa.adidas.usecases.*
import org.koin.dsl.module

val useCaseModule = module {

    single { GetProductsUseCase(get()) }
    single { GetProductUseCase(get()) }
    single { GetReviewsUseCase(get()) }
    single { CreateReviewUseCase(get()) }
    single { FetchProductsUseCase(get()) }
}