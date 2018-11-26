package com.lotuss.ordersisprocessed.waiter.menu

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lotuss.ordersisprocessed.R
import com.lotuss.ordersisprocessed.data.food.GetMenu
import kotlinx.android.synthetic.main.menu_fragment.view.*

class FragmentMenu: Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.menu_fragment, container, false)
        val recyclerView: RecyclerView = view.recycler_menu
        val adapter = MenuSectionAdapter(layoutInflater, GetMenu.sectionList)
        Log.d("LIST", GetMenu.sectionList[0].sectionList[1].title)
        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        recyclerView.adapter = adapter
        return view
    }
}