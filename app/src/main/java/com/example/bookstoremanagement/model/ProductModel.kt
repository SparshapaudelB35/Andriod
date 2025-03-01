package com.example.bookstoremanagement.model

import android.os.Parcel
import android.os.Parcelable

data class ProductModel(
    var productId: String = "",
    var productName: String = "",
    var productAuthor: String = "",  // Author property
    var productDesc: String = "",    // Description property
    var price: Int = 0,              // Price of the product
    var imageUrl: String = ""       // Image URL for the product
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readString() ?: "",
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(productId)
        parcel.writeString(productName)
        parcel.writeString(productAuthor)
        parcel.writeString(productDesc)
        parcel.writeInt(price)
        parcel.writeString(imageUrl)  // Writing imageUrl to parcel
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ProductModel> {
        override fun createFromParcel(parcel: Parcel): ProductModel {
            return ProductModel(parcel)
        }

        override fun newArray(size: Int): Array<ProductModel?> {
            return arrayOfNulls(size)
        }
    }
}
