package com.altoke.altoke

import android.os.Parcelable
import androidx.versionedparcelable.VersionedParcelize
import com.google.firebase.firestore.DocumentReference
import com.google.type.DateTime
import org.w3c.dom.Document
import java.util.*
import kotlin.collections.HashMap

data class Store(val name: String,
                 val description: String,
                 val headerImagePath: String?,
                 val logoImagePath: String?){
    val hasHeaderImage
        get() = headerImagePath != null

    val hasLogoImage
        get() = logoImagePath != null
}

data class Product(val title: String,
                   val description: String,
                   val price: Double,
                   var productRef: String = "",
                   val imageUrl: String? = null,
                   val storeRef: DocumentReference? = null){

    constructor() : this("", "", 0.0)

    val hasImage
        get() = imageUrl != null
}

class Cart(val user: DocumentReference) {
    // Hashmap of a product and its quantity.
    val items = HashMap<Product, Int>()
}

data class Order(var state: OrderState? = null,
                 var storeName: String? = null,
                 var totalCost: Double? = null,
                 var date: Calendar? = null,
                 var orderCart: Cart? = null,
                 var orderRef: DocumentReference? = null){
}

enum class OrderState {
    PENDING_APPROVAL,
    ON_ITS_WAY,
    COMPLETED,
    CANCELLED,
}