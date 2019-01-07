package com.lotuss.ordersisprocessed.waiter.orders

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.lotuss.ordersisprocessed.InfoDialog
import com.lotuss.ordersisprocessed.R
import com.lotuss.ordersisprocessed.cook.CancelDialog
import com.lotuss.ordersisprocessed.data.orders.Order
import kotlinx.android.synthetic.main.order_waiter_item.view.*
import java.text.SimpleDateFormat
import java.util.*

class OrdersAdapter(private val layoutInflater: LayoutInflater, private val items: MutableList<Order>):
        RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View = layoutInflater.inflate(R.layout.order_waiter_item, parent, false)
        return OrderHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    private fun formatDate(date: Date):String{
        val format = SimpleDateFormat("hh:mm")
        return format.format(date)
    }

    private fun cancelOrder(order: Order, context: Context){
        val activity: AppCompatActivity = context as AppCompatActivity
        val dialog = CancelDialog()
        dialog.orderId = order.id
        dialog.from = 1
        dialog.show(activity.supportFragmentManager.beginTransaction(), "DialogFragment")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val orderHolder: OrderHolder = holder as OrderHolder
        val order: Order = items[position]
        orderHolder.orderId.text = orderHolder.orderId.context.resources.getString(R.string.order_id, order.id)
        orderHolder.date.text = formatDate(order.date)
        var statusText = ""
        when(order.status){
            0 -> {
                statusText = orderHolder.status.context.resources.getString(R.string.sent)
                orderHolder.status.text = statusText
                val image: Drawable = orderHolder.status.context.resources.getDrawable(R.drawable.waiting)
                orderHolder.status.setCompoundDrawablesWithIntrinsicBounds(image, null, null, null)
            }
            1 -> {
                statusText = orderHolder.status.context.resources.getString(R.string.cooking)
                orderHolder.status.text = statusText
                val image: Drawable = orderHolder.status.context.resources.getDrawable(R.drawable.cooking)
                orderHolder.status.setCompoundDrawablesWithIntrinsicBounds(image, null, null, null)
            }
            2 -> {
                statusText = orderHolder.status.context.resources.getString(R.string.cooked)
                orderHolder.status.text = statusText
                val image: Drawable = orderHolder.status.context.resources.getDrawable(R.drawable.done)
                orderHolder.status.setCompoundDrawablesWithIntrinsicBounds(image, null, null, null)
            }
            3 ->{
                statusText = orderHolder.status.context.resources.getString(R.string.cancelled)
                orderHolder.status.text = statusText
                orderHolder.cancel.visibility = View.GONE
                val image: Drawable = orderHolder.status.context.resources.getDrawable(R.drawable.cancelled)
                orderHolder.status.setCompoundDrawablesWithIntrinsicBounds(image, null, null, null)
            }
        }
        orderHolder.orderView.setOnClickListener {
            val context: Context = orderHolder.orderView.context
            val activity: AppCompatActivity = context as AppCompatActivity
            val dialog = InfoDialog()
            dialog.setInfo(order.id, order.orderList, statusText, order.desc)
            dialog.show(activity.supportFragmentManager.beginTransaction(), "DialogFragment")
        }

        orderHolder.cancel.setOnClickListener {
            cancelOrder(order, orderHolder.cancel.context)
            if(order.status == 3)
                orderHolder.cancel.visibility = View.GONE
        }
    }

    class OrderHolder(view: View): RecyclerView.ViewHolder(view){
        val orderView: CardView = view.order
        val orderId: TextView = view.order_id
        val date: TextView = view.date
        val status: TextView = view.status
        val cancel: Button = view.cancel
    }
}