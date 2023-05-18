package com.romanvoytyuk.shoplist.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class GetShopItemListUsageCase(private val repository: ShopListRepository){
    fun getShopItemList(): LiveData<List<ShopItem>> {
        return repository.getShopItemList()
    }

}