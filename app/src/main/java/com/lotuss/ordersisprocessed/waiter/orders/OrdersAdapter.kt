package com.lotuss.ordersisprocessed.waiter.orders

import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.lotuss.ordersisprocessed.R
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

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val orderHolder: OrderHolder = holder as OrderHolder
        val order: Order = items[position]
        orderHolder.orderId.text = orderHolder.orderId.context.resources.getString(R.string.order_id, order.id)
        orderHolder.date.text = formatDate(order.date)
        var statusText: String = ""
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
        }
        orderHolder.moreBtn.setOnClickListener {
            val context: Context = orderHolder.moreBtn.context
            val activity: AppCompatActivity = context as AppCompatActivity
            val dialog = InfoDialog()
            dialog.setInfo(order.id, order.orderList, statusText, order.desc)
            dialog.show(activity.supportFragmentManager.beginTransaction(), "DialogFragment")
        }
    }

    class OrderHolder(view: View): RecyclerView.ViewHolder(view){
        val orderId: TextView = view.order_id
        val date: TextView = view.date
        val status: TextView = view.status
        val moreBtn: Button = view.more_btn
    }
}