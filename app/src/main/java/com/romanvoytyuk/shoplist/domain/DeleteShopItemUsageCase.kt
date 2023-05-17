package com.romanvoytyuk.shoplist.domain

class DeleteShopItemUsageCase(private val repository: ShopListRepository) {
    fun deleteShopItem(shopItem: ShopItem) {
        repository.deleteShopItem(shopItem)
    }
}