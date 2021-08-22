package com.altoke.altoke

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import java.util.*

class CartElementView(context: Context, attributeSet: AttributeSet? = null): FrameLayout(context, attributeSet) {
    private val productPrice: TextView
    private val productTitle: TextView
    private val productQuantity: TextView

    init {
        val view = View.inflate(context, R.layout.view_cart_element, this)

        productQuantity = view.findViewById(R.id.product_quantity)
        productPrice = view.findViewById(R.id.product_cost)
        productTitle = view.findViewById(R.id.product_title)

        // val testProduct = Product(
        //         "Completo Mojadísimo",
        //         "No description",
        //         2000.0
        // )

        // setProduct(testProduct)
    }

    fun setProduct(product: Product) {
        productTitle.text = product.title

        productPrice.text = resources.getString(R.string.product_cost, product.price.toString())
    }
}