package com.romanvoytyuk.shoplist.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.romanvoytyuk.shoplist.data.ShopListRepositoryImp
import com.romanvoytyuk.shoplist.domain.DeleteShopItemUsageCase
import com.romanvoytyuk.shoplist.domain.EditShopItemUsageCase
import com.romanvoytyuk.shoplist.domain.GetShopItemListUsageCase
import com.romanvoytyuk.shoplist.domain.ShopItem
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ShopListRepositoryImp(application)

    private val getShopItemListUsageCase = GetShopItemListUsageCase(repository)
    private val deleteShopItemUsageCase = DeleteShopItemUsageCase(repository)
    private val editShopItemUsageCase = EditShopItemUsageCase(repository)

    val listOfShopItem = getShopItemListUsageCase.getShopItemList()

    fun deleteShopItem(shopItem: ShopItem) {
        viewModelScope.launch {
            deleteShopItemUsageCase.deleteShopItem(shopItem)
        }


    }

    fun changeEnableState(shopItem: ShopItem) {
        viewModelScope.launch {
            editShopItemUsageCase.editShopItem(shopItem.copy(enable = !shopItem.enable))
        }

    }

}