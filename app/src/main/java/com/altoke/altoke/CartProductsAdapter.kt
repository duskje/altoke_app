package com.altoke.altoke

import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import java.lang.reflect.Method

class CartProductsAdapter(private var products: List<Product>, val callbackUpdate: (Int) -> Unit) : RecyclerView.Adapter<CartProductsAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val view = itemView as CartElementView
        val setProduct = {product: Product -> view.setProduct(product)}
        val cancelButton = itemView.findViewById<MaterialButton>(R.id.cancel_button)
    }

    init {
        callbackUpdate(totalCost.toInt())
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(CartElementView(parent.context))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product: Product = products.get(position)

        holder.setProduct(product)

        // If the user presses the 'cancel' button on one of the cardviews, we will...
        holder.cancelButton.setOnClickListener{
            // Clone the list and remove the view
            val newList = products.toMutableList()
            newList.removeAt(holder.adapterPosition)

            // and then update the recyclerview
            update(newList.toList())
        }

        // We will also call this to update the total cost TextView of the activity
        Log.d("price", totalCost.toInt().toString())
    }

    override fun getItemCount(): Int {
        return products.size
    }

    val totalCost: Double
        get() {
            var total = 0.0

            for(product in products){
                total += product.price
            }

            return total
        }

    fun update(newList: List<Product>){
        val callback = CartDiffUtilCallback(products, newList)
        val diff = DiffUtil.calculateDiff(callback)
        products = newList

        diff.dispatchUpdatesTo(this)

        callbackUpdate(totalCost.toInt())
    }
}

