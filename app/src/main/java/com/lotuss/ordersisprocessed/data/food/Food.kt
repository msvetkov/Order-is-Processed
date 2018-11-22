package com.lotuss.ordersisprocessed.data.food

data class Food(
        val title: String = "",
        val prise: Double = 0.0,
        val type: Int = 0,
        var count: Int = 0
)