package com.lotuss.ordersisprocessed.waiter

import android.graphics.Color
import android.graphics.PorterDuff
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
import com.google.android.material.tabs.TabItem

import com.lotuss.ordersisprocessed.R
import com.lotuss.ordersisprocessed.waiter.menu.FragmentMenu
import com.lotuss.ordersisprocessed.waiter.orders.FragmentOrders
import kotlinx.android.synthetic.main.activity_waiter.*

class WaiterActivity : AppCompatActivity() {

    lateinit var toolbar: Toolbar

    private var sectionsPagerAdapter: SectionsPagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_waiter)

        toolbar = this.toolbar_widget
        setSupportActionBar(toolbar)
        supportActionBar!!.title = resources.getString(R.string.title_menu)
        sectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)
        container.adapter = sectionsPagerAdapter

        container.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))
        tabs.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(container))

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

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
        menuInflater.inflate(R.menu.menu_main2, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.action_settings) {
            return true
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
