package com.example.inventory.model

import android.graphics.Bitmap
import android.os.Parcelable
import android.widget.ImageView
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "things_table")
data class Things(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val price: Int,
    val quantity: Int,
    val supplier: String
//    val image: Bitmap
):Parcelable
