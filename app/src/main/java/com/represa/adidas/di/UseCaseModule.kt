package com.represa.adidas.di

import com.represa.adidas.ui.viewmodels.ProductDetailViewModel
import com.represa.adidas.ui.viewmodels.ProductViewModel
import com.represa.adidas.usecases.*
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val useCaseModule = module {

    viewModel { ProductViewModel(androidContext(), get(), get(), get(), get(named(ERROR_FLOW))) }
    viewModel { ProductDetailViewModel(androidContext(),  get(), get(), get(), get(), get(named(ERROR_FLOW))) }

    single { GetProductsUseCase(get()) }
    single { GetProductUseCase(get()) }
    single { GetReviewsUseCase(get()) }
    single { CreateReviewUseCase(get()) }
    single { FetchProductsUseCase(get()) }
    single { FetchReviewsUseCase(get()) }
    single { GetProductsFilteredUseCase(get()) }
}