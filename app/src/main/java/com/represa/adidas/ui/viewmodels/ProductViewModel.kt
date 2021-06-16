package com.represa.adidas.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.represa.adidas.data.Repository
import kotlinx.coroutines.launch

class ProductViewModel(var repository: Repository) : ViewModel() {

    fun test(){
        viewModelScope.launch {
            var product = repository.getProducts()
            Log.e("PRODUCTS: ", product[0].toString())
        }
    }
}