package com.romanvoytyuk.shoplist.domain

class GetShopItemUsageCase(private val repository: ShopListRepository){
    fun getShopItem(id: Int): ShopItem {
        return repository.getShopItem(id)
    }
}