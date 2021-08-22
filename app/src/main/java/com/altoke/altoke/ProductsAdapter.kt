package com.altoke.altoke

import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton

class ProductsAdapter(private var products: List<Product>, val callbackUpdate: (Product) -> Unit) : RecyclerView.Adapter<ProductsAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardTitle = itemView.findViewById<TextView>(R.id.card_title)
        val cardDescription = itemView.findViewById<TextView>(R.id.card_description)

        val view = itemView as ProductCardView
        val setProduct = {product: Product -> view.setProduct(product)}
        val addToCartButton = itemView.findViewById<MaterialButton>(R.id.add_to_cart_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //val context = parent.context
        return ViewHolder(ProductCardView(parent.context))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product: Product = products.get(position)

        holder.setProduct(product)

        holder.addToCartButton.setOnClickListener{
            callbackUpdate(product)
        }

        //val title = holder.cardTitle
        //val description = holder.cardDescription

        //title.text = product.title
        //description.text = product.description

        // Log.d("totalcost", getTotalCost().toString())
    }

    override fun getItemCount(): Int {
        return products.size
    }

    fun update(newList: List<Product>){
        val callback = ProductsDiffUtilCallback(products, newList)
        val diff = DiffUtil.calculateDiff(callback)
        products = newList

        diff.dispatchUpdatesTo(this)
    }
}