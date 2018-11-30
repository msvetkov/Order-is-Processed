package com.lotuss.ordersisprocessed.waiter.menu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lotuss.ordersisprocessed.R
import com.lotuss.ordersisprocessed.data.food.MenuSection
import kotlinx.android.synthetic.main.menu_section_item.view.*
import com.github.florent37.expansionpanel.viewgroup.ExpansionLayoutCollection
import com.github.florent37.expansionpanel.ExpansionLayout
import com.lotuss.ordersisprocessed.R.id.expansionLayout







class MenuSectionAdapter(private val layoutInflater: LayoutInflater, private val items: MutableList<MenuSection>):
        RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private val expansionsCollection = ExpansionLayoutCollection()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View = layoutInflater.inflate(R.layout.menu_section_item, parent, false)
        return SectionHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val sectionHolder: SectionHolder = holder as SectionHolder
        val section: MenuSection = items[position]
        expansionsCollection.add(holder.expansionLayout)
        expansionsCollection.openOnlyOne(false)
        sectionHolder.title.text = section.title
        val foodAdapter = MenuFoodAdapter(layoutInflater, items[position].sectionList)
        sectionHolder.foodRecyclerView.layoutManager = LinearLayoutManager(sectionHolder.foodRecyclerView.context,
                RecyclerView.VERTICAL, false)
        sectionHolder.foodRecyclerView.adapter = foodAdapter
        val dividerItemDecoration: RecyclerView.ItemDecoration  =
                DividerItemDecorator(sectionHolder.foodRecyclerView.context.resources.getDrawable(R.drawable.divider))
        sectionHolder.foodRecyclerView.addItemDecoration(dividerItemDecoration)
    }

    class SectionHolder(view: View): RecyclerView.ViewHolder(view){
        val expansionLayout = view.expansionLayout!!
        val title = view.title!!
        val foodRecyclerView: RecyclerView = view.recycler_food

    }
}