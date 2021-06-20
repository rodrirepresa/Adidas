package com.represa.adidas.data.database.entities

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "review",
primaryKeys = ["id", "productId", "locale", "rating", "text"])
data class ReviewEntity(
    @NonNull
    var id: Int,

    @NonNull
    var productId: String,

    @NonNull
    val locale: String,

    @NonNull
    val rating: Int,

    @NonNull
    val text: String
)