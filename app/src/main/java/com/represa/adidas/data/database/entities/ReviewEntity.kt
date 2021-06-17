package com.represa.adidas.data.database.entities

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "review")
data class ReviewEntity(
    @PrimaryKey
    @NonNull
    var productId: String,

    @ColumnInfo
    @NonNull
    val locale: String,

    @ColumnInfo
    @NonNull
    val rating: String,

    @ColumnInfo
    @NonNull
    val text: String
)