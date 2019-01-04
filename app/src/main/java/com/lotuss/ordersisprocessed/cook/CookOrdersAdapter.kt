package com.lotuss.ordersisprocessed.cook

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
import com.google.firebase.database.FirebaseDatabase
import com.lotuss.ordersisprocessed.R
import com.lotuss.ordersisprocessed.data.orders.Order
import com.lotuss.ordersisprocessed.InfoDialog
import com.lotuss.ordersisprocessed.waiter.menu.OrderDialog
import kotlinx.android.synthetic.main.order_cook_item.view.*
import java.text.SimpleDateFormat
import java.util.*

class CookOrdersAdapter(private val layoutInflater: LayoutInflater, private val items: MutableList<Order>):
        RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View = layoutInflater.inflate(R.layout.order_cook_item, parent, false)
        return OrderHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    private fun formatDate(date: Date):String{
        val format = SimpleDateFormat("hh:mm")
        return format.format(date)
    }

    private fun acceptOrder(order: Order){
        val database = FirebaseDatabase.getInstance()
        val reference = database.getReference("orders")
        reference.child(order.id.toString()).child("checkedByWaiter").setValue(false)
        reference.child(order.id.toString()).child("status").setValue(order.status + 1)

    }

    private fun cancelOrder(order: Order, context: Context){
        val activity: AppCompatActivity = context as AppCompatActivity
        val dialog = CancelDialog()
        dialog.orderId = order.id
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
                orderHolder.accept.text = orderHolder.status.context.resources.getString(R.string.done)
                statusText = orderHolder.status.context.resources.getString(R.string.cooking)
                orderHolder.status.text = statusText
                val image: Drawable = orderHolder.status.context.resources.getDrawable(R.drawable.cooking)
                orderHolder.status.setCompoundDrawablesWithIntrinsicBounds(image, null, null, null)
            }
            2 -> {
                orderHolder.accept.isClickable = false
                orderHolder.cancel.isClickable = false
                statusText = orderHolder.status.context.resources.getString(R.string.cooked)
                orderHolder.status.text = statusText
                val image: Drawable = orderHolder.status.context.resources.getDrawable(R.drawable.done)
                orderHolder.status.setCompoundDrawablesWithIntrinsicBounds(image, null, null, null)
            }
            3 ->{
                orderHolder.accept.isClickable = false
                orderHolder.cancel.isClickable = false
                statusText = orderHolder.status.context.resources.getString(R.string.cancelled)
                orderHolder.status.text = statusText
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

        orderHolder.accept.setOnClickListener {
            acceptOrder(order)
        }
        orderHolder.cancel.setOnClickListener {
            cancelOrder(order, orderHolder.cancel.context)
        }
    }

    class OrderHolder(view: View): RecyclerView.ViewHolder(view){
        val orderView: CardView = view.order
        val orderId: TextView = view.order_id
        val date: TextView = view.date
        val status: TextView = view.status
        val accept: Button = view.accept
        val cancel: Button = view.cancel
    }
}