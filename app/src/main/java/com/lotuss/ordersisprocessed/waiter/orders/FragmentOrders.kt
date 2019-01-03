package com.lotuss.ordersisprocessed.waiter.orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.lotuss.ordersisprocessed.R
import com.lotuss.ordersisprocessed.data.orders.Order
import kotlinx.android.synthetic.main.orders_fragment.view.*



class FragmentOrders: Fragment(){

    private var orderList: MutableList<Order> = mutableListOf()
    private val database = FirebaseDatabase.getInstance()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.orders_fragment, container, false)
        val progressBar: ProgressBar = view.progress_circular
        val recyclerView: RecyclerView = view.recycler_orders
        val adapter = OrdersAdapter(layoutInflater, orderList)
        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        recyclerView.adapter = adapter
        getOrderList(progressBar, adapter)
        return view
    }

    private fun getOrderList(progressBar: ProgressBar, adapter: OrdersAdapter){
        val orderReference = database.getReference("orders")
        orderReference.addValueEventListener(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                progressBar.visibility = View.VISIBLE
                orderList.clear()
                for (ds in dataSnapshot.children) {
                    val order: Order = ds.getValue(Order::class.java)!!
                    orderList.add(order)
                    adapter.notifyDataSetChanged()
                }
                progressBar.visibility = View.GONE
                adapter.notifyDataSetChanged()
            }
        })
    }
}