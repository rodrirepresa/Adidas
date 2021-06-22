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
    fun addition_isCorrectt() {
        val testContextProvider = TestContextProvider()
        //productDetailViewModel.changeContextProvider(testContextProvider)

        productDetailViewModel.getProduct("HI334")


        val value = productDetailViewModel.product.getOrAwaitValue()


        assert(value.id == "HI334")
    }
}