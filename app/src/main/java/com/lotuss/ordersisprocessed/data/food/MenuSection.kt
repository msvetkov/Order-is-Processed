package com.lotuss.ordersisprocessed.data.food

data class MenuSection(
        val title: String = "",
        val type: Int = 0,
        val sectionList: MutableList<Food>
)