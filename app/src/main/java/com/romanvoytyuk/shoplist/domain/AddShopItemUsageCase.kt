package com.romanvoytyuk.shoplist.domain

class AddShopItemUsageCase(private val repository: ShopListRepository) {
     suspend fun addShopItem(shopItem: ShopItem) {
         repository.addShopItem(shopItem)
     }
}