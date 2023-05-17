package com.romanvoytyuk.shoplist.domain

interface ShopListRepository {
    fun getShopItem(id: Int): ShopItem
    fun deleteShopItem(shopItem: ShopItem)
    fun editShopItem(shopItem: ShopItem)
    fun getShopItemList(): List<ShopItem>
    fun addShopItem(item: ShopItem)
}