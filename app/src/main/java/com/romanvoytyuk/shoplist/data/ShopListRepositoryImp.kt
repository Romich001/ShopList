package com.romanvoytyuk.shoplist.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.romanvoytyuk.shoplist.domain.ShopItem
import com.romanvoytyuk.shoplist.domain.ShopListRepository
import kotlin.random.Random

object ShopListRepositoryImp : ShopListRepository {

    private val shopItemList = sortedSetOf<ShopItem>({ o1, o2 -> o1.id.compareTo(o2.id) })
    private val shopItemListLD = MutableLiveData<List<ShopItem>>()
    private var autoIncrementId = 0

    init {
        repeat(10) {
            addShopItem(ShopItem("name$it", it, Random.nextBoolean()))
        }
    }

    override fun getShopItem(id: Int): ShopItem {
        return shopItemList.find { it.id == id } ?: throw RuntimeException("wrong id")
    }


    override fun deleteShopItem(shopItem: ShopItem) {
        shopItemList.remove(shopItem)
        updateShopItemList()
    }

    override fun editShopItem(shopItem: ShopItem) {
        val oldElement = getShopItem(shopItem.id)
        shopItemList.remove(oldElement)
        shopItemList.add(shopItem)
        updateShopItemList()
    }

    override fun getShopItemList(): LiveData<List<ShopItem>> {
        return shopItemListLD
    }

    override fun addShopItem(shopItem: ShopItem) {
        if (shopItem.id == ShopItem.UNDEFINED_ID) {
            shopItem.id = autoIncrementId++
        }
        shopItemList.add(shopItem)
        updateShopItemList()
    }

    private fun updateShopItemList() {
        shopItemListLD.value = shopItemList.toList()
    }
}