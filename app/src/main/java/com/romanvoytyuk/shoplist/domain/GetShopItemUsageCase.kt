package com.romanvoytyuk.shoplist.domain

class GetShopItemUsageCase(private val repository: ShopListRepository){
    suspend fun getShopItem(id: Int): ShopItem {
        return repository.getShopItem(id)
    }
}