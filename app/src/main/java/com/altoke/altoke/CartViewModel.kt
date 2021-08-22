package com.altoke.altoke

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CartViewModel : ViewModel(){
    private val cart: MutableLiveData<List<Product>> by lazy {
        MutableLiveData<List<Product>>().also {
        }
    }

    fun setCart(products: List<Product>){
        cart.value = products
    }
}