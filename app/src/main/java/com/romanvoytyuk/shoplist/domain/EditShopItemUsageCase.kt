package com.romanvoytyuk.shoplist.domain

class EditShopItemUsageCase(private val repository: ShopListRepository) {
    fun editShopItem(shopItem: ShopItem) {
        repository.editShopItem(shopItem)
    }
}