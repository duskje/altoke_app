package com.altoke.altoke

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.constraintlayout.solver.state.State
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.setMargins
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.local.ReferenceSet
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {
    private val selectedProducts = mutableListOf<Product>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val database = Firebase.firestore

//        val order = hashMapOf(
//            "time" to FieldValue.serverTimestamp(),
//            "order_cart" to hashMapOf(
//                "product_id1" to "2",
//                "product_id2" to "10"
//            ),
//            "final_price" to 5000,
//            "user" to "Alice",
//            "store" to "Manhattan",
//            "confirmed" to false,
//            "address" to "Rengo 500"
//        )
//
//        val product = hashMapOf(
//            "name" to "Completo Mojado",
//            "store" to "Manhattan",
//            "price" to 5000,
//            "image_path" to "path",
//            "description" to "hello"
//        )
//
//        val store = hashMapOf(
//            "header_image_path" to "path",
//            "logo_image_path" to "path",
//            "description" to "hello"
//        )
//
//        val user = hashMapOf(
//            "first_name" to "Alice",
//            "last_name" to "Lewandowsky",
//            "email" to "doscanoa2017@udec.cl"
//        )
//
//        val storeRef = database.collection("stores")
//            .document("Manhattan")
//        storeRef.set(store)
//
//        val productRef = database.collection("products")
//            .add(product).addOnSuccessListener { documentReference ->
//                storeRef.collection("products").add(mapOf("reference" to documentReference))
//            }
//
//        val orderRef = database.collection("orders")
//            .add(order)
//            .addOnSuccessListener { documentReference ->
//                Log.d("Docsnippets", "DocumentSnapshot added with ID: ${documentReference}")
//            }
//            .addOnFailureListener { e ->
//                Log.w("docsnippets", "erroradding document", e)
//            }

        val rvProducts = findViewById<RecyclerView>(R.id.recyclerview_products)

        val adapter = ProductsAdapter(listOf(), { product -> addToCart(product)})

        rvProducts.adapter = adapter
        rvProducts.layoutManager = GridLayoutManager(this, 1)

        val productsList = listOf(
                Product("Completo Mojado", "Destruyan a Talca", 1290.0, imageUrl = "https://finde.latercera.com/wp-content/uploads/2018/11/La-Fulana-4-ok.jpg"),
                Product("Sopaipilla con Manjar", "Horrible", 500.0),
                Product("Empanada", "Lo mejor", 2490.0, "https://t1.rg.ltmcdn.com/es/images/8/5/5/empanadas_al_horno_34558_orig.jpg"),
                Product("Handroll", "Descripción", 1000.0, "https://misterpanko.com/wp-content/uploads/2021/02/IMG-20180217-WA0022.jpg"),
                Product("Sandwich Chorreante", "Algo cerdo para el bajón", 3990.0)
        )

        val firebaseProducts = mutableListOf<Product>()

        val documentReference = database.collection("products")
            .get().addOnSuccessListener { documentSnaps ->
                for (document in documentSnaps){
                    val product = document.toObject<Product>()
                    product.productRef = document.id
                    firebaseProducts.add(product)

                    Log.d("firebase", document.id)
                    Log.d("firebase", firebaseProducts.toString())
                }

                adapter.update(firebaseProducts.toList())
            }

        val userAuth = FirebaseAuth.getInstance()

        val currentUserUid = userAuth.currentUser!!.uid
        val currentUserToken = userAuth.currentUser!!.getIdToken(true).addOnSuccessListener { tokenTask ->
            Log.d("print", tokenTask.token.toString())
        }

        //val user = hashMapOf(
        //        "role" to "customer"
        //)

        //val userRef = database.collection("users").document(currentUserUid).set(user)

        val userRole = database.collection("users").document(currentUserUid).get().addOnSuccessListener {doc ->
            val role = doc.getString("role")
            Log.d("print", doc.toString())
            Log.d("print", role.toString())
        }.addOnFailureListener(){e ->
            Log.d("print", "Failed with", e)
        }

        val navigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        navigation.selectedItemId = R.id.store_page

        navigation.setOnNavigationItemSelectedListener { item ->
            Log.d("Nav drawer", item.itemId.toString())

            when(item.itemId) {
                R.id.store_page -> {
                    true
                }

                R.id.cart_page -> {
                    intent = Intent(this, CartActivity::class.java).apply {
                        val gson = Gson()
                        putExtra("SELECTED_PRODUCTS", gson.toJson(selectedProducts))
                    }

                    startActivity(intent)

                    true
                }
                R.id.orders_page -> {
                    intent = Intent(this, CustomerOrdersActivity::class.java).apply {
                        //putExtra(EXTRA_MESSAGE, message)
                    }

                    startActivity(intent)

                    true
                }
                else -> false
            }
        }
    }

    private fun addToCart(product: Product){
        Toast.makeText(this, "Agregado al carro", Toast.LENGTH_SHORT).show()
        selectedProducts.add(product)

        Log.d("items", selectedProducts.toString())
    }

    override fun onBackPressed() {
        finishAffinity()
    }
}