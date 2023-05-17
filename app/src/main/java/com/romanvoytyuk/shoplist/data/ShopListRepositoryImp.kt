package com.romanvoytyuk.shoplist.data

import com.romanvoytyuk.shoplist.domain.ShopItem
import com.romanvoytyuk.shoplist.domain.ShopListRepository

class ShopListRepositoryImp() : ShopListRepository {

    private val shopItemList = mutableListOf<ShopItem>()
    private var autoIncrementId = 0

    override fun getShopItem(id: Int): ShopItem {
        return shopItemList.find { it.id == id } ?: throw RuntimeException("wrong id")
    }

    override fun deleteShopItem(shopItem: ShopItem) {
        shopItemList.remove(shopItem)
    }

    override fun editShopItem(shopItem: ShopItem) {
        val oldElement = getShopItem(shopItem.id)
        shopItemList.remove(oldElement)
        shopItemList.add(shopItem)
    }

    override fun getShopItemList(): List<ShopItem> {
        return shopItemList
    }

    override fun addShopItem(shopItem: ShopItem) {
        if (shopItem.id == ShopItem.UNDEFINED_ID) {
            shopItem.id = autoIncrementId++
        }
        shopItemList.add(shopItem)
    }
}