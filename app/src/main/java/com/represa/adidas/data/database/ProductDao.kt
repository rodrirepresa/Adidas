package com.represa.adidas.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.represa.adidas.data.database.entities.ProductEntity
import com.represa.adidas.data.database.entities.ReviewEntity
import com.represa.adidas.data.network.model.Product
import com.represa.adidas.data.network.model.Review
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertProducts(products: List<ProductEntity>)

    @Query("SELECT * FROM product")
    fun getProducts() : Flow<List<ProductEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertReview(review: ReviewEntity)

    @Query("SELECT * FROM review")
    fun getReviews() : Flow<List<ReviewEntity>>


}