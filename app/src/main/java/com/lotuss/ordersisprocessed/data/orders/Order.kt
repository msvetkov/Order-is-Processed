package com.lotuss.ordersisprocessed.data.orders

import com.lotuss.ordersisprocessed.data.food.Food
import java.util.*

data class Order(
        val id: Int = 0,
        val foodList: MutableList<Food> = mutableListOf(),
        val date: Date = Date(0, 0, 0, 0, 0),
        val waiter: String = "waiter",
        val table: Int = 0
)