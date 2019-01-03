package com.lotuss.ordersisprocessed.waiter.orders

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lotuss.ordersisprocessed.R
import com.lotuss.ordersisprocessed.data.food.Food
import com.lotuss.ordersisprocessed.waiter.menu.OrderFoodAdapter
import kotlinx.android.synthetic.main.info_dialog.view.*

class InfoDialog: DialogFragment(){
    private var orderId: Int = 0
    private var list: MutableList<Food> = mutableListOf()
    private var status: String = "status"
    private var desc: String = "desk"

    fun setInfo(orderId: Int, list: MutableList<Food>, status: String, desc: String){
        this.orderId = orderId
        this.list = list
        this.status = status
        this.desc = desc
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val v: View = inflater.inflate(R.layout.info_dialog, null)
        val orderFoodAdapter = OrderFoodAdapter(inflater, list)
        val recycler: RecyclerView = v.food_recycler
        recycler.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        recycler.adapter = orderFoodAdapter
        v.desc.text = desc
        v.status.text = status
        v.order_id.text = context!!.resources.getString(R.string.order_id, orderId)
        v.ok.setOnClickListener{dismiss()}
        return v
    }
}