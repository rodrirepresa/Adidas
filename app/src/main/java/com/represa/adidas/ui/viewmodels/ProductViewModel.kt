package com.represa.adidas.ui.viewmodels

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.represa.adidas.R
import com.represa.adidas.data.exception.GeneralException
import com.represa.adidas.usecases.*
import com.represa.adidas.util.ConnectivityLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

class ProductViewModel(
    /*var getProductUseCase: GetProductUseCase,
    var getProductsUseCase: GetProductsUseCase,
    var getReviewsUseCase: GetReviewsUseCase,
    var createReviewUseCase: CreateReviewUseCase,*/
    val context: Context,
    var fetchProductsUseCase: FetchProductsUseCase,
    var getProductsFilteredUseCase: GetProductsFilteredUseCase,
    private val connectivityLiveData: ConnectivityLiveData,
    private val errorStream: MutableStateFlow<Throwable?>
) : ViewModel() {

    val errorLiveData: LiveData<Throwable?>
        get() = errorStream.asLiveData()

    val internetConection = connectivityLiveData

    var showTitle = mutableStateOf(true)

    var searchFlow = MutableStateFlow("")

    val productSearched = searchFlow.debounce(200L).flatMapLatest { search ->
        getProductsFilteredUseCase.invoke(search)
    }.asLiveData()

    fun populateDatabase() {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                fetchProductsUseCase.invoke(Unit)
            }.onFailure {
                errorStream.value =
                    GeneralException(context.getString(R.string.error_api_exception))
            }
        }
    }

    fun updateSearchQuery(key: String) {
        searchFlow.value = key
    }

}