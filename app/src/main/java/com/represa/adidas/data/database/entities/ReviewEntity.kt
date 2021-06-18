package com.represa.adidas.data.database.entities

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "review")
data class ReviewEntity(
    @PrimaryKey(autoGenerate = true)
    @NonNull
    var id: Int,

    @ColumnInfo
    @NonNull
    var productId: String,

    @ColumnInfo
    @NonNull
    val locale: String,

    @ColumnInfo
    @NonNull
    val rating: Int,

    @ColumnInfo
    @NonNull
    val text: String
)