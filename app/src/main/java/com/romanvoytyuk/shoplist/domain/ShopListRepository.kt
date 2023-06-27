package com.romanvoytyuk.shoplist.domain

import androidx.lifecycle.LiveData

interface ShopListRepository {
    suspend fun getShopItem(id: Int): ShopItem
    suspend fun deleteShopItem(shopItem: ShopItem)
    suspend fun editShopItem(shopItem: ShopItem)
    fun getShopItemList(): LiveData<List<ShopItem>>
    suspend fun addShopItem(shopItem: ShopItem)
}