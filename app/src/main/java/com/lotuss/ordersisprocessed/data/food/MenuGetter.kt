package com.lotuss.ordersisprocessed.data.food

import android.content.Context
import com.google.firebase.database.*
import com.google.firebase.database.DataSnapshot
import com.lotuss.ordersisprocessed.data.auth.AuthWithFirebase


class MenuGetter {
    companion object {
        var menuList: MutableList<Food> = mutableListOf()
        var sectionList: MutableList<MenuSection> = mutableListOf()
        private val database = FirebaseDatabase.getInstance()

        fun getMenuFromDatabase(context: Context){
            val menuReference = database.getReference("menu")
            menuReference.addValueEventListener(object: ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {
                }

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    menuList.clear()
                    for (ds in dataSnapshot.children) {
                        val food: Food = ds.getValue(Food::class.java)!!
                        menuList.add(food)
                    }
                    getMenuSectionFromDatabase(context)
                }
            })
        }

        fun getMenuSectionFromDatabase(context: Context){
            val sectionReference = database.getReference("section")
            sectionReference.addValueEventListener(object: ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {
                }

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    sectionList.clear()
                    for (ds in dataSnapshot.children){
                        val title = ds.child("title").getValue(String::class.java)!!
                        val type = ds.child("type").getValue(Int::class.java)!!
                        val mutableList: MutableList<Food> = menuList.filter {it.type == type} as MutableList<Food>
                        val section = MenuSection(title, type , mutableList)
                        sectionList.add(section)

                    }
                    AuthWithFirebase.startWaiterActivity(context)
                }
            })
        }
    }
}