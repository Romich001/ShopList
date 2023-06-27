package com.romanvoytyuk.shoplist.domain

class EditShopItemUsageCase(private val repository: ShopListRepository) {
    suspend fun editShopItem(shopItem: ShopItem) {
        repository.editShopItem(shopItem)
    }
}