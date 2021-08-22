package com.altoke.altoke

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        val gson = Gson()

        val bundle: Bundle? = intent.extras
        class Token: TypeToken<List<Product>>()
        val message = gson.fromJson<List<Product>>(bundle?.getString("SELECTED_PRODUCTS"), Token().type)
        Log.d("items", message.toString())

        //val productsList = mutableListOf(
        //        Product("completo", "asdasd", 3990.0),
        //        Product("pan con queso", "las tazas sobre el mantel", 4990.0)
        //)

        val rvProducts = findViewById<RecyclerView>(R.id.rv_cart_products)
        val adapter = CartProductsAdapter(message, {totalCost -> updateTotalCostHeadline(totalCost)})
        rvProducts.adapter = adapter

        // The recycleview viewholders won't inflate properly if the layoutmanager
        // is set to a linearlayoutmanager instance
        // https://stackoverflow.com/questions/35904409/item-in-recyclerview-not-filling-its-width-match-parent/48603061

        val layoutManager = GridLayoutManager(this, 1)
        rvProducts.layoutManager = layoutManager
    }

    private fun updateTotalCostHeadline(totalCost: Int) {
        Log.d("total_cost", totalCost.toString())

        val totalCostView = findViewById<TextView>(R.id.order_total_cost)
        totalCostView.text =  resources.getString(R.string.order_total_cost, totalCost.toString())
    }
}