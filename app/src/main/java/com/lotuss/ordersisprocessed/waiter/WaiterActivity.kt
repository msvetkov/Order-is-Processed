package com.lotuss.ordersisprocessed.waiter


import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import com.google.android.material.tabs.TabLayout
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.DialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.lotuss.ordersisprocessed.AuthActivity

import com.lotuss.ordersisprocessed.R
import com.lotuss.ordersisprocessed.data.food.Food
import com.lotuss.ordersisprocessed.data.orders.Order
import com.lotuss.ordersisprocessed.data.orders.OrderManager
import com.lotuss.ordersisprocessed.waiter.menu.FragmentMenu
import com.lotuss.ordersisprocessed.waiter.menu.OrderDialog
import com.lotuss.ordersisprocessed.waiter.orders.FragmentOrders
import kotlinx.android.synthetic.main.activity_waiter.*

@Suppress("UNUSED_ANONYMOUS_PARAMETER")
class WaiterActivity : AppCompatActivity() {

    lateinit var toolbar: Toolbar
    private var sectionsPagerAdapter: SectionsPagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_waiter)
        OrderManager.order = Order()
        toolbar = this.toolbar_widget
        setSupportActionBar(toolbar)
        supportActionBar!!.title = resources.getString(R.string.title_menu)
        sectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)
        container.adapter = sectionsPagerAdapter
        container.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))
        tabs.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(container))
        fab.setOnClickListener {
            showDialog()
        }

        onPageChange()
    }

    fun showDialog(){
        var isEmptyOrder = true
        for(f in OrderManager.order.foodList)
            if (f.count != 0)
                isEmptyOrder = false
        if(isEmptyOrder){
            Snackbar.make(this.main_content, R.string.your_order_is_empty, Snackbar.LENGTH_LONG).show()
            return
        }

        for(f in OrderManager.order.foodList)
            if (f.count != 0)
                OrderManager.order.orderList.add(f)
        val dialog = OrderDialog()
        dialog.show(supportFragmentManager, "DialogFragment")
    }

    private fun onPageChange(){
        container.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))
        tabs.setOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> {
                        container.currentItem = 0
                        toolbar.title = resources.getString(R.string.title_menu)
                        tab.icon!!.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN)
                        fab.show()
                    }
                    1 -> {
                        container.currentItem = 1
                        toolbar.title = resources.getString(R.string.title_orders)
                        tab.icon!!.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN)
                        fab.hide()
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> {
                        tab.icon!!.setColorFilter(resources.getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_IN)
                    }
                    1 -> {
                        tab.icon!!.setColorFilter(resources.getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_IN)
                    }
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        when(id){
            R.id.action_settings -> return true
            R.id.action_exit -> {
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(this, AuthActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            lateinit var fragment: Fragment
            when (position) {
                0 -> {
                    fragment = FragmentMenu()
                }
                1 -> {
                    fragment = FragmentOrders()
                }
            }
            return fragment
        }

        override fun getCount(): Int {
            return 2
        }
    }
}
