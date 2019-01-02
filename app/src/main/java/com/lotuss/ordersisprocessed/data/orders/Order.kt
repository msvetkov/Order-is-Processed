package com.lotuss.ordersisprocessed.data.orders

import com.lotuss.ordersisprocessed.data.food.Food
import com.lotuss.ordersisprocessed.data.food.GetMenu
import java.util.*

data class Order(
        val id: Int = 0,
        val foodList: MutableList<Food> = GetMenu.menuList,
        val orderList: MutableList<Food> = mutableListOf(),
        val date: Date = Date(0, 0, 0, 0, 0),
        val waiter: String = "waiter",
        val table: Int = 0
)