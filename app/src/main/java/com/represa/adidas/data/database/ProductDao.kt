package com.represa.adidas.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.represa.adidas.data.database.entities.ProductEntity
import com.represa.adidas.data.database.entities.ReviewEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProducts(products: List<ProductEntity>)

    @Query("SELECT * FROM product")
    fun getProducts() : Flow<List<ProductEntity>>

    @Query("SELECT * FROM product WHERE name LIKE '%' || :key || '%' OR description LIKE '%' || :key || '%'")
    fun getProductsFiltered(key: String) : Flow<List<ProductEntity>>

    @Query("SELECT * FROM product WHERE id = :idProduct")
    fun getProduct(idProduct : String) : ProductEntity

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertReview(review: ReviewEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertReviews(review: List<ReviewEntity>)

    @Query("SELECT * FROM review WHERE productId = :productId")
    fun getReviews(productId: String) : Flow<List<ReviewEntity>>


}