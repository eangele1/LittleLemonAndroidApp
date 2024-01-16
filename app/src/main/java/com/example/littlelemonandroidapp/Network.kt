package com.example.littlelemonandroidapp

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MenuNetwork(
    @SerialName("menu")
    val menu: List<MenuItemNetwork>
)

@Serializable
data class MenuItemNetwork(
    val id: Int,
    val title: String,
    val description: String,
    val price: Int,
    val image: String,
    val category: String
){
    fun toMenuItemRoom() = MenuItemRoom(
        id = id,
        title = title,
        description = description,
        price = price,
        image = image,
        category = category
    )
}
