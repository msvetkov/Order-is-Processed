package com.lotuss.ordersisprocessed.waiter.orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.lotuss.ordersisprocessed.R
import kotlinx.android.synthetic.main.activity_waiter.*

class FragmentOrders: Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.orders_fragment, container, false)
        return view
    }
}