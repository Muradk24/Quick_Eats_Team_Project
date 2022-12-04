package com.example.quick_eats_team_project

data class MenuResponse(
    var menu: List<MenuItemModel>? = null,
    var exception: Exception? = null
)

class MenuItemModel (
    var catId: Long? = null,
    var menuId: Int? = null,
    var menuItem: String? = null,
    var price: String? = null,
    var menuImage: String? = null,

    )
