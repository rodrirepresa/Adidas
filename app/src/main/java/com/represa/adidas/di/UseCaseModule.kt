package com.represa.adidas.di

import com.represa.adidas.ui.viewmodels.ProductDetailViewModel
import com.represa.adidas.ui.viewmodels.ProductViewModel
import com.represa.adidas.usecases.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {

    viewModel { ProductViewModel(get(), get(), get(), get(), get()) }
    viewModel { ProductDetailViewModel(get(), get(), get()) }

    single { GetProductsUseCase(get()) }
    single { GetProductUseCase(get()) }
    single { GetReviewsUseCase(get()) }
    single { CreateReviewUseCase(get()) }
    single { FetchProductsUseCase(get()) }
    single { FetchReviewsUseCase(get()) }
}