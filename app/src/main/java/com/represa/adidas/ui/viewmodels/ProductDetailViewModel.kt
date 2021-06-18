package com.represa.adidas.ui.viewmodels

import androidx.lifecycle.*
import com.represa.adidas.data.database.entities.ProductEntity
import com.represa.adidas.data.database.entities.ReviewEntity
import com.represa.adidas.data.network.model.Review
import com.represa.adidas.usecases.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductDetailViewModel(
    private val getProductUseCase: GetProductUseCase,
    private val fetchReviewsUseCase: FetchReviewsUseCase,
    private val getReviewsUseCase: GetReviewsUseCase,
    private val createReviewUseCase: CreateReviewUseCase
) : ViewModel() {

    private val _product = MutableLiveData<ProductEntity>()
    val product: LiveData<ProductEntity>
        get() = _product

    fun getReviews(productId: String) = getReviewsUseCase.invoke(productId).asLiveData()

    fun getProduct(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _product.postValue(getProductUseCase.invoke(id))
        }
    }

    fun fetchReviews(productId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            fetchReviewsUseCase.invoke(productId)
        }
    }

    fun createReview(productId: String?, text: String) {
        productId?.let {
            viewModelScope.launch(Dispatchers.IO) {
                createReviewUseCase.invoke(
                    Review(
                        productId = productId,
                        locale = "EN",
                        rating = 5,
                        text = text
                    )
                )
            }
        }
    }

}