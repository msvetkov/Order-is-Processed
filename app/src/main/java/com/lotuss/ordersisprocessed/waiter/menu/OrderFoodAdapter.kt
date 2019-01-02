package com.lotuss.ordersisprocessed.waiter.menu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lotuss.ordersisprocessed.R
import com.lotuss.ordersisprocessed.data.food.Food
import kotlinx.android.synthetic.main.order_send_food_item.view.*

class OrderFoodAdapter(private val layoutInflater: LayoutInflater, private val items: MutableList<Food>):
        RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View = layoutInflater.inflate(R.layout.order_send_food_item, parent, false)
        return OrderFoodHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val foodHolder: OrderFoodHolder = holder as OrderFoodHolder
        val food = items[position]
        foodHolder.title.text = food.title
        foodHolder.count.text = foodHolder.count.context.resources.getString(R.string.pieces, food.count)
    }

    class OrderFoodHolder(view: View): RecyclerView.ViewHolder(view){
        val title:TextView = view.order_title
        val count:TextView = view.order_count
    }
}