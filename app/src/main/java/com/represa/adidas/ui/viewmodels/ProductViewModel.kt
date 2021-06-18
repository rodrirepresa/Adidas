package com.represa.adidas.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.represa.adidas.usecases.*
import com.represa.adidas.util.ConnectivityLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

class ProductViewModel(
    var getProductUseCase: GetProductUseCase,
    var getProductsUseCase: GetProductsUseCase,
    var getReviewsUseCase: GetReviewsUseCase,
    var createReviewUseCase: CreateReviewUseCase,
    var fetchProductsUseCase: FetchProductsUseCase,
    var getProductsFilteredUseCase: GetProductsFilteredUseCase,
    private val connectivityLiveData: ConnectivityLiveData
) : ViewModel() {

    val internetConection = connectivityLiveData

    val allProducts = getProductsUseCase.invoke(Unit).asLiveData()
    private val searchFlow = MutableStateFlow("")

    val productSearched = searchFlow.debounce(200L).flatMapLatest { search ->
        getProductsFilteredUseCase.invoke(search)
    }.asLiveData()

    fun populateDatabase() {
        viewModelScope.launch(Dispatchers.IO) {
            fetchProductsUseCase.invoke(Unit)
        }
    }

    fun updateSearchQuery(key: String){
        searchFlow.value = key
    }

}