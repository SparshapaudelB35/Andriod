package com.example.bookstoremanagement.utils

import com.example.bookstoremanagement.model.ProductModel

object CartManager {
    var cartItems = mutableListOf<ProductModel>()

    fun addToCart(product: ProductModel) {
        cartItems.add(product) // Add product to the cart
    }

    fun clearCart() {
        cartItems.clear() // Clear all items from the cart
    }

    fun removeFromCart(product: ProductModel) {
        cartItems.remove(product) // Remove specific product from the cart
    }

    fun getTotalPrice(): Int {
        return cartItems.sumOf { it.price } // Sum up the prices of all items in the cart
    }
}
