package com.altoke.altoke

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.getField
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import java.util.*

class CustomerOrdersActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_orders)

        val ordersList = mutableListOf(
                Order(
                        OrderState.ON_ITS_WAY,
                        "Sanhattan",
                        2000.0,
                        Calendar.getInstance()
                ),
                Order(
                        OrderState.PENDING_APPROVAL,
                        "Sanhattan",
                        4000.0,
                        Calendar.getInstance()
                ),
                Order(
                        OrderState.COMPLETED,
                        "Sanhattan",
                        5000.0,
                        Calendar.getInstance()
                )
        )

        val database = Firebase.firestore

        val rvProducts = findViewById<RecyclerView>(R.id.rv_customer_orders)
        val adapter = CustomerOrdersAdapter(ordersList)
        rvProducts.adapter = adapter

        val firebaseOrders = mutableListOf<Order>()

        val userAuth = FirebaseAuth.getInstance()

        val currentUserUid = userAuth.currentUser!!.uid

        val documentReference = database.collection("users")
                .document(currentUserUid)
                .collection("orders")
                .get().addOnSuccessListener { documentSnaps ->
                    for (document in documentSnaps){
                        val order = document.data
                                .get("orderData")
                        Order(order.get())
                        // val order = document.getDocumentReference("order")?.get()?.addOnSuccessListener { snap ->
                        //     val data = snap.data
                        //     Log.d("firebase_date", snap.getTimestamp("date")?.toDate().toString())

                        //     Log.d("firebase", data.toString())
                        // }
                        // firebaseOrders.add(order)
                        Log.d("firebase", order.get("orderData").toString())

                        Log.d("firebase", document.id)
                        Log.d("firebase", order.toString())
                    }
                    // adapter.update(firebaseOrders.toList())
                }

        val layoutManager = GridLayoutManager(this, 1)
        rvProducts.layoutManager = layoutManager
    }
}
