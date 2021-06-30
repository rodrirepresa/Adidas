package com.represa.adidas


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.represa.adidas.ui.viewmodels.ProductViewModel
import org.koin.test.KoinTest
import org.koin.test.inject
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.represa.adidas.di.appModule
import com.represa.adidas.di.useCaseModule
import com.represa.adidas.ui.viewmodels.ProductDetailViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.*
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules

/**
 * To run the test succesfully, please comment the gradle:app line 115 (implementation("androidx.activity:activity-compose:$compose_version")
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest: KoinTest {

    private val productViewModel by inject<ProductViewModel>()
    private val productDetailViewModel by inject<ProductDetailViewModel>()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()


    @Before
    fun before() {
        loadKoinModules(listOf(appModule, useCaseModule))
        productViewModel.populateDatabase()
    }

    @ExperimentalCoroutinesApi
    @Ignore("StateFlow testing")
    fun `product_viewmodel_product_searched`() {
        var productSearched = productViewModel.productSearched
        assert(productSearched.value.isNullOrEmpty())
        productViewModel.updateSearchQuery("product")
        var t = productViewModel.productSearched.getOrAwaitValue()
        assert(t.isNotEmpty())
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `product_viewmodel_update_search_query`() {
        var searchValue = productViewModel.searchFlow
        assert(searchValue.value == "")
        productViewModel.updateSearchQuery("test")
        assert(searchValue.value == "test")
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `product_detail_viewmodel_get_review`() {
        var reviews = productDetailViewModel.getReviews("NO_RESULT")
        assert(reviews.value.isNullOrEmpty())
        productDetailViewModel.createReview("NO_RESULT", "TEST") {}
        reviews = productDetailViewModel.getReviews("NO_RESULT")
        assert(reviews.value.isNullOrEmpty())
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `product_detail_viewmodel_get_product`() {
        var product = productDetailViewModel.product.value
        assert(product == null)
        productDetailViewModel.getProduct("HI334")
        product = productDetailViewModel.product.getOrAwaitValue()
        assert(product != null)
        assert(product.id ==  "HI334")
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `product_detail_viewmodel_fetch_reviews_rating_null`() {
        var reviews = productDetailViewModel.getReviews("NO_RESULTS")
        assert(reviews.value == null)
        productDetailViewModel.createReview("NO_RESULTS", "NO_RESULTS") { }
        var newreviews = productDetailViewModel.getReviews("NO_RESULTS").getOrAwaitValue()
        assert(newreviews.isNullOrEmpty())
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `product_detail_viewmodel_create_and_fetch_reviews_rating_not_null`() {
        var reviews = productDetailViewModel.getReviews("HI334")
        assert(reviews.value.isNullOrEmpty())
        productDetailViewModel.setRating(5)
        productDetailViewModel.createReview("HI334", "HI334") { }
        var newreviews = productDetailViewModel.getReviews("HI334").getOrAwaitValue()
        assert(newreviews != null)
        for(i in newreviews!!){
            assert(i.productId == "HI334")
        }
    }

}