package com.represa.adidas


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.represa.adidas.ui.viewmodels.ProductViewModel
import org.junit.Test
import org.junit.Rule
import org.koin.test.KoinTest
import org.koin.test.inject
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.represa.adidas.di.appModule
import com.represa.adidas.di.useCaseModule
import com.represa.adidas.ui.viewmodels.ProductDetailViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
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
    @Test
    fun `error_live_data`() {
        //val testContextProvider = TestContextProvider()
        //productDetailViewModel.changeContextProvider(testContextProvider)
        assert(productViewModel.errorLiveData.value == null)
        productDetailViewModel.getProduct("")
        assert(productViewModel.errorLiveData.value != null)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `product_viewmodel_product_searched`() {
        //val testContextProvider = TestContextProvider()
        //productDetailViewModel.changeContextProvider(testContextProvider)
        var productSearched = productViewModel.productSearched
        assert(productSearched.value.isNullOrEmpty())
        productViewModel.updateSearchQuery("product")
        var t = productViewModel.productSearched.getOrAwaitValue()
        assert(productSearched.value!!.isNotEmpty())
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `product_viewmodel_update_search_query`() {
        //val testContextProvider = TestContextProvider()
        //productDetailViewModel.changeContextProvider(testContextProvider)
        var searchValue = productViewModel.searchFlow
        assert(searchValue.value == "")
        productViewModel.updateSearchQuery("test")
        assert(searchValue.value == "test")
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `product_detail_viewmodel_get_review`() {
        //val testContextProvider = TestContextProvider()
        //productDetailViewModel.changeContextProvider(testContextProvider)
        var reviews = productDetailViewModel.getReviews("NO_RESULT")
        assert(reviews.value.isNullOrEmpty())
        productDetailViewModel.createReview("NO_RESULT", "TEST", {} )
        reviews = productDetailViewModel.getReviews("NO_RESULT")
        assert(reviews.value.isNullOrEmpty())
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `product_detail_viewmodel_get_product`() {
        //val testContextProvider = TestContextProvider()
        //productDetailViewModel.changeContextProvider(testContextProvider)
        var product = productDetailViewModel.product.value
        assert(product == null)
        productDetailViewModel.getProduct("HI334")
        product = productDetailViewModel.product.getOrAwaitValue()
        assert(product != null)
        assert(product.id ==  "HI334")
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `product_detail_viewmodel_fetch_reviews`() {
        //val testContextProvider = TestContextProvider()
        //productDetailViewModel.changeContextProvider(testContextProvider)
        var reviews = productDetailViewModel.getReviews("HI334")
        assert(reviews.value == null)
        productDetailViewModel.createReview("HI334", "HI334", {  })
        var newreviews = productDetailViewModel.getReviews("HI334").getOrAwaitValue()
        assert(newreviews != null)
        /*for(i in reviews.value!!){
            assert(i.productId == "HI334")
        }*/
    }


}