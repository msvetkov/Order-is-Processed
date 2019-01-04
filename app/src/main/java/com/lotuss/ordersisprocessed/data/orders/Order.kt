package com.lotuss.ordersisprocessed.data.orders

import com.lotuss.ordersisprocessed.data.food.Food
import com.lotuss.ordersisprocessed.data.food.MenuGetter
import java.util.*

data class Order(
        var id: Int = 0,
        val foodList: MutableList<Food> = MenuGetter.menuList,
        val orderList: MutableList<Food> = mutableListOf(),
        var date: Date = Date(0, 0, 0, 0, 0),
        var waiter: String = "waiter",
        var desc: String = "desk",
        var status: Int = 0,
        var checkedByWaiter: Boolean = true,
        var checkedByCook: Boolean = false
)