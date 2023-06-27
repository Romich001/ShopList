package com.romanvoytyuk.shoplist.domain

data class ShopItem(
    val name: String,
    val price: Int,
    val enable: Boolean,
    var id: Int = UNDEFINED_ID
) {
    companion object {
        const val UNDEFINED_ID = 0
    }
}