package com.example.quick_eats_team_project

data class Response(
    var category: List<CategoryModel>? = null,
    var exception: Exception? = null
)


data class CategoryModel(
    var cat_id: Long? = null,
    var cat_name: String? = null,
    var cat_rating: Int? = null,
    var cat_image: String? = null,

)
