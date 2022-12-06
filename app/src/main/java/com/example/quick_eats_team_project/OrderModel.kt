package com.example.quick_eats_team_project

import com.google.type.DateTime

data class OrderResponse(
    var order: List<OrderModel>? = null,
    var exception: Exception? = null
)

 class OrderModel(
    var status: Long? = null,
    var orderDate: String? = null,
    var userId: String? = null

 ){
     var id: String? = null
     var price: String? = null
     var menuId: Long? = null
     var menuItem: String? = null
     var menuImage: String? = null
 }
