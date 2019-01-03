package com.lotuss.ordersisprocessed.waiter.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lotuss.ordersisprocessed.R
import com.lotuss.ordersisprocessed.data.food.MenuGetter
import kotlinx.android.synthetic.main.activity_waiter.*
import kotlinx.android.synthetic.main.menu_fragment.view.*

class FragmentMenu: Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.menu_fragment, container, false)
        val recyclerView: RecyclerView = view.recycler_menu
        val adapter = MenuSectionAdapter(layoutInflater, MenuGetter.sectionList)
        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        recyclerView.adapter = adapter

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0 && activity!!.fab.visibility == View.VISIBLE) {
                    activity!!.fab.hide()
                } else if (dy < 0 && activity!!.fab.visibility != View.VISIBLE) {
                    activity!!.fab.show()
                }
            }
        })
        return view
    }
}