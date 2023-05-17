package com.romanvoytyuk.shoplist.domain

data class ShopItem(
    val name: String,
    val price: Int,
    val enable: Boolean,
    var id: Int = UNDEFINED_ID
) {
    companion object {
        const val UNDEFINED_ID = -1
    }
}

class AddShopItemUsageCase(private val repository: ShopListRepository) {
     fun addShopItem(item: ShopItem) {
         repository.addShopItem(item)
     }
}

class GetShopItemListUsageCase(private val repository: ShopListRepository){
    fun getShopItemList(): List<ShopItem> {
        return repository.getShopItemList()
    }
    
}
class EditShopItemUsageCase(private val repository: ShopListRepository) {
    fun editShopItem(shopItem: ShopItem) {
        repository.editShopItem(shopItem)
    }
}
class DeleteShopItemUsageCase(private val repository: ShopListRepository) {
    fun deleteShopItem(shopItem: ShopItem) {
        repository.deleteShopItem(shopItem)
    }
}

class GetShopItemUsageCase(private val repository: ShopListRepository){
    fun getShopItem(id: Int): ShopItem {
        return repository.getShopItem(id)
    }
}

interface ShopListRepository {
    fun getShopItem(id: Int): ShopItem
    fun deleteShopItem(shopItem: ShopItem)
    fun editShopItem(shopItem: ShopItem)
    fun getShopItemList(): List<ShopItem>
    fun addShopItem(item: ShopItem)
}