package com.represa.adidas.ui.viewmodels

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.*
import androidx.palette.graphics.Palette
import com.represa.adidas.R
import com.represa.adidas.data.database.entities.ProductEntity
import com.represa.adidas.data.network.model.Review
import com.represa.adidas.usecases.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL

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

    private var rating = 3

    fun getReviews(productId: String) = getReviewsUseCase.invoke(productId).asLiveData()

    fun getProduct(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                _product.postValue(getProductUseCase.invoke(id))
            }.onFailure {
                errorStream.value = Throwable(context.getString(R.string.error_general_exception))
            }
        }
    }

    fun fetchReviews(productId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                fetchReviewsUseCase.invoke(productId)
            }.onFailure {
                errorStream.value = Throwable(context.getString(R.string.error_api_exception))
            }
        }
    }

    fun createReview(productId: String?, text: String, onSucces: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                productId?.let {
                    createReviewUseCase.invoke(
                        Review(
                            productId = productId,
                            locale = "EN",
                            rating = rating,
                            text = text
                        )
                    )
                }
            }.onFailure {
                errorStream.value = Throwable(context.getString(R.string.error_general_exception))
            }.onSuccess {
                onSucces.invoke()
            }
        }
    }

    fun setRating(rate: Int){
        rating = rate
    }

    fun setUpBackgroundColor(imageUrl: String, onSuccess: (palette: Palette?) -> Unit) {
        var image: Bitmap? = null
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                val url = URL(imageUrl)
                image = BitmapFactory.decodeStream(url.openConnection().getInputStream())
            }.onSuccess {
                withContext(Dispatchers.Main) {
                    image?.let {
                        val builder = Palette.Builder(image!!)
                        val palette = builder.generate { palette: Palette? ->
                            onSuccess.invoke(palette)
                        }
                    }
                }
            }
        }
    }
}