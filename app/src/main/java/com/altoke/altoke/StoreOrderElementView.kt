package com.altoke.altoke

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*

class StoreOrderElementView(context: Context, attributeSet: AttributeSet? = null): FrameLayout(context, attributeSet) {
    private val orderCode: TextView
    private val orderState: TextView
    private val orderTotalCost: TextView
    private val orderDate: TextView

    init {
        val view = View.inflate(context, R.layout.view_store_order_element, this)

        orderCode = view.findViewById(R.id.order_code)
        orderState = view.findViewById(R.id.order_state)
        orderTotalCost = view.findViewById(R.id.order_total_cost)
        orderDate = view.findViewById(R.id.order_date)

        val testOrder = Order(
                OrderState.PENDING_APPROVAL,
                "Sanhattan",
                2000.0,
                Calendar.getInstance()
        )

        setOrder(testOrder)
    }

    fun setOrder(order: Order){
        when (order.state) {
            OrderState.ON_ITS_WAY -> {
                orderState.text = resources.getString(
                        R.string.order_state,
                        "Pedido en camino"
                )
            }
            OrderState.PENDING_APPROVAL -> {
                orderState.text = resources.getString(
                        R.string.order_state,
                        "Aprobación Pendiente"
                )
            }
            OrderState.COMPLETED -> {
                orderState.text = resources.getString(
                        R.string.order_state,
                        "Pedido completado"
                )
            }
            OrderState.CANCELLED -> {
                orderState.text = resources.getString(
                        R.string.order_state,
                        "Orden Cancelada"
                )
            }
            else -> {}
        }

        val costFormat = order.totalCost.toString()
        orderTotalCost.text = resources.getString(R.string.order_total_cost, costFormat)

        val date = order.date

        if (date != null) {
            val dateFormatter = SimpleDateFormat("HH:mm:ss dd/MM/yyyy", Locale.getDefault())
            val dateString = dateFormatter.format(date.time)

            orderDate.text = resources.getString(R.string.order_date, dateString)
        }
        // orderCode.text = resources.getString(R.string.order_code, order.order)
    }
}