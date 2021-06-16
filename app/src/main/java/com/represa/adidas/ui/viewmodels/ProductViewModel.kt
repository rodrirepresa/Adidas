package com.represa.adidas.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.represa.adidas.data.Repository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ProductViewModel(var repository: Repository) : ViewModel() {

    fun test(){
        viewModelScope.launch {
            var product = repository.getProducts()
            product.collect { it ->
                Log.e("hola:", it.toString())
            }
        }
    }
}