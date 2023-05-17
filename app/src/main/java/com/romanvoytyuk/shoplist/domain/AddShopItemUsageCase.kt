package com.romanvoytyuk.shoplist.domain

class AddShopItemUsageCase(private val repository: ShopListRepository) {
     fun addShopItem(shopItem: ShopItem) {
         repository.addShopItem(shopItem)
     }
}