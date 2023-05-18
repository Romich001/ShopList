package com.romanvoytyuk.shoplist.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.romanvoytyuk.shoplist.data.ShopListRepositoryImp
import com.romanvoytyuk.shoplist.domain.DeleteShopItemUsageCase
import com.romanvoytyuk.shoplist.domain.EditShopItemUsageCase
import com.romanvoytyuk.shoplist.domain.GetShopItemListUsageCase
import com.romanvoytyuk.shoplist.domain.ShopItem

class MainViewModel : ViewModel() {

    private val repository = ShopListRepositoryImp

    private val getShopItemListUsageCase = GetShopItemListUsageCase(repository)
    private val deleteShopItemUsageCase = DeleteShopItemUsageCase(repository)
    private val editShopItemUsageCase = EditShopItemUsageCase(repository)

    val listOfShopItem = MutableLiveData<List<ShopItem>>()


    fun getListShopItem() {
        listOfShopItem.value = getShopItemListUsageCase.getShopItemList()
    }

    fun deleteShopItem(shopItem: ShopItem) {
        deleteShopItemUsageCase.deleteShopItem(shopItem)
        getListShopItem()
    }

    fun changeEnableState(shopItem: ShopItem) {
        editShopItemUsageCase.editShopItem(shopItem.copy(enable = !shopItem.enable))
        getListShopItem()

    }





}