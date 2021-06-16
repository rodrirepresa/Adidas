package com.represa.adidas.data.database.entities

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product")
data class ProductEntity(
    @PrimaryKey
    @NonNull
    var id: String,

    @ColumnInfo
    @NonNull
    val currency: String,

    @ColumnInfo
    @NonNull
    val price: Int,

    @ColumnInfo
    @NonNull
    val name: String,

    @ColumnInfo
    @NonNull
    val description: String,

    @ColumnInfo
    @NonNull
    val imgUrl: String
)