package com.romanvoytyuk.shoplist.domain

import androidx.lifecycle.LiveData

interface ShopListRepository {
    fun getShopItem(id: Int): ShopItem
    fun deleteShopItem(shopItem: ShopItem)
    fun editShopItem(shopItem: ShopItem)
    fun getShopItemList(): LiveData<List<ShopItem>>
    fun addShopItem(shopItem: ShopItem)
}