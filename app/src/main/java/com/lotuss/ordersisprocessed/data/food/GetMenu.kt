package com.lotuss.ordersisprocessed.data.food

import com.google.firebase.database.*
import com.google.firebase.database.DataSnapshot



class GetMenu {
    companion object {
        var menuList: MutableList<Food> = mutableListOf()

        fun getMenuFromDatabase(){
            val database = FirebaseDatabase.getInstance()
            val myRef = database.getReference("menu")
            myRef.addValueEventListener(object: ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {

                }

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (ds in dataSnapshot.children) {
                        val food: Food = ds.getValue(Food::class.java)!!
                        menuList.add(food)
                    }
                }
            })
        }
    }
}