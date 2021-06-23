package com.represa.adidas.ui.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.represa.adidas.R
import com.represa.adidas.data.exception.ProductsException
import com.represa.adidas.usecases.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class SplashViewModel(
    private val context: Context,
    private var fetchProductsUseCase: FetchProductsUseCase,
    private val errorStream: MutableStateFlow<Throwable?>
) : ViewModel() {

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
}