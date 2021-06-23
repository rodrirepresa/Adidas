package com.represa.adidas.ui.viewmodels

import android.content.Context
import androidx.lifecycle.*
import com.represa.adidas.R
import com.represa.adidas.data.database.entities.ProductEntity
import com.represa.adidas.data.exception.GeneralException
import com.represa.adidas.data.exception.ProductDetailException
import com.represa.adidas.data.network.model.Review
import com.represa.adidas.usecases.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ProductDetailViewModel(
    private val context: Context,
    private val getProductUseCase: GetProductUseCase,
    private val fetchReviewsUseCase: FetchReviewsUseCase,
    private val getReviewsUseCase: GetReviewsUseCase,
    private val createReviewUseCase: CreateReviewUseCase,
    private val errorStream: MutableStateFlow<Throwable?>
) : ViewModel() {

    private val _product = MutableLiveData<ProductEntity>()
    val product: LiveData<ProductEntity>
        get() = _product

    private var rating: Int? = null

    fun getReviews(productId: String) = getReviewsUseCase.invoke(productId).asLiveData()

    fun getProduct(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                _product.postValue(getProductUseCase.invoke(id))
            }.onFailure {
                errorStream.value =
                    ProductDetailException(context.getString(R.string.error_api_exception))
            }
        }
        fetchReviews(id)
    }

    private fun fetchReviews(productId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                fetchReviewsUseCase.invoke(productId)
            }.onFailure {
                errorStream.value =
                    ProductDetailException(context.getString(R.string.error_api_exception))
            }
        }
    }

    fun createReview(productId: String?, text: String, onSucces: () -> Unit) {
        if (rating == null) {
            errorStream.value = Throwable(context.getString(R.string.error_rating))
        } else {
            viewModelScope.launch(Dispatchers.IO) {
                runCatching {
                    productId?.let {
                        createReviewUseCase.invoke(
                            Review(
                                productId = productId,
                                locale = "EN",
                                rating = rating!!,
                                text = text
                            )
                        )
                    }
                }.onFailure {
                    errorStream.value =
                        GeneralException(context.getString(R.string.error_general_exception))
                }.onSuccess {
                    onSucces.invoke()
                }
            }
        }
    }

    fun setRating(rate: Int) {
        rating = rate
    }
}