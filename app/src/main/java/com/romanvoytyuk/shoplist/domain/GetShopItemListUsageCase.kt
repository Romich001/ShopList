package com.romanvoytyuk.shoplist.domain

class GetShopItemListUsageCase(private val repository: ShopListRepository){
    fun getShopItemList(): List<ShopItem> {
        return repository.getShopItemList()
    }

}