package com.altoke.altoke

import android.app.Application

class CartSingleton : Application() {
    private var cart: List<Product> = listOf()

    fun setCart(products: List<Product>) {
        cart = products
    }

    fun getCart(): List<Product>{
        return cart
    }
}