package com.lotuss.ordersisprocessed.waiter.menu

import android.app.ActionBar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.lotuss.ordersisprocessed.R

class FragmentMenu: Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.menu_fragment, container, false)

        return view
    }
}