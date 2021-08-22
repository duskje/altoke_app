package com.altoke.altoke

import android.content.Context
import android.media.Image
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.card.MaterialCardView
import com.squareup.picasso.Picasso

class ProductCardView(context: Context, attributeSet: AttributeSet? = null): FrameLayout(context, attributeSet) {
    private val cardTitle: TextView
    private val cardDescription: TextView
    private val cardImage: ImageView

    init {
        val view = View.inflate(context, R.layout.view_product_card, this)

        cardTitle = view.findViewById(R.id.card_title)
        cardDescription = view.findViewById(R.id.card_description)
        cardImage = view.findViewById(R.id.card_image)
    }

    fun setProduct(product: Product){
        cardTitle.text = product.title
        cardDescription.text = product.description

        val imageUrl = product.imageUrl

        if (imageUrl != null){
            Picasso.with(context).load(imageUrl).into(cardImage)
        } else {
            cardImage.setImageResource(R.drawable.card_placeholder)
        }
    }
}