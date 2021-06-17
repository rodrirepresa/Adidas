package com.represa.adidas.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.represa.adidas.usecases.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductViewModel(
    var getProductUseCase: GetProductUseCase,
    var getProductsUseCase: GetProductsUseCase,
    var getReviewsUseCase: GetReviewsUseCase,
    var createReviewUseCase: CreateReviewUseCase,
    var fetchProductsUseCase: FetchProductsUseCase
) : ViewModel() {

    val allProducts = getProductsUseCase.invoke(Unit).asLiveData()

    fun populateDatabase() {
        viewModelScope.launch(Dispatchers.IO) {
            fetchProductsUseCase.invoke(Unit)
        }
    }

}