package com.daftmobile.listtap.data.model

data class Element(
    val value: Int,
    val color: ElementColor
)

enum class ElementColor {
     BLUE, RED
}
