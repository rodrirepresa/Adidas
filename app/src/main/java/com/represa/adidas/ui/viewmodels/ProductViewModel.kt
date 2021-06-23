package com.represa.adidas.ui.viewmodels

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.represa.adidas.R
import com.represa.adidas.data.exception.ProductsException
import com.represa.adidas.usecases.*
import com.represa.adidas.util.ConnectivityLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductViewModel(
    val context: Context,
    var getProductsUseCase: GetProductsUseCase,
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
                    ProductsException(context.getString(R.string.error_api_exception))
            }
        }
    }

    fun showCorrectEmptyProductsMessage(bindText: (title: String, subTitle: String) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                var products = getProductsUseCase.invoke(Unit)
                var title = ""
                var subTitle = ""
                if (products.isNullOrEmpty()) {
                    title = context.getString(R.string.server_error_title)
                    subTitle = context.getString(R.string.server_error_title)
                } else {
                    title = context.getString(R.string.no_results)
                    subTitle = "Your search " + searchFlow.value + " did not match any product"
                }
                withContext(Dispatchers.Main) {
                    bindText.invoke(title, subTitle)
                }
            }.onFailure {
                errorStream.value =
                    ProductsException(context.getString(R.string.error_api_exception))
            }
        }
    }

    fun updateSearchQuery(key: String) {
        searchFlow.value = key
    }

    fun checkFirstConnection(showDialog: () -> Unit) {
        var connection = connectivityLiveData.checkConnection()
        if (!connection) {
            showDialog.invoke()
        }
    }

}