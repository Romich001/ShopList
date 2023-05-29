package com.romanvoytyuk.shoplist.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.romanvoytyuk.shoplist.data.ShopListRepositoryImp
import com.romanvoytyuk.shoplist.domain.AddShopItemUsageCase
import com.romanvoytyuk.shoplist.domain.EditShopItemUsageCase
import com.romanvoytyuk.shoplist.domain.GetShopItemUsageCase
import com.romanvoytyuk.shoplist.domain.ShopItem


class ShopItemViewModel : ViewModel() {

    private val repository = ShopListRepositoryImp

    private val addShopItemUsageCase = AddShopItemUsageCase(repository)
    private val editShopItemUsageCase = EditShopItemUsageCase(repository)
    private val getShopItemUsageCase = GetShopItemUsageCase(repository)

    private val _errorInputName = MutableLiveData<Boolean>()
    private val _errorInputPrice = MutableLiveData<Boolean>()
    private val _shopItem = MutableLiveData<ShopItem>()
    private val _shouldCloseScreen = MutableLiveData<Unit>()


    val shouldCloseScreen: LiveData<Unit>
        get() = _shouldCloseScreen
    val shopItem: LiveData<ShopItem>
        get() = _shopItem
    val errorInputName: LiveData<Boolean>
        get() = _errorInputName
    val errorInputPrice: LiveData<Boolean>
        get() = _errorInputPrice



    fun addShopItem(inputName: String?, inputPrice: String?) {
        val name = parsName(inputName)
        val price = parsPrice(inputPrice)
        val fieldsValid = validateInput(name, price)
        if (fieldsValid) {
            val shopItem = ShopItem(name, price, true)
            addShopItemUsageCase.addShopItem(shopItem)
            finishWork()
        }

    }

    fun editShopItem(inputName: String?, inputPrice: String?) {
        val name = parsName(inputName)
        val price = parsPrice(inputPrice)
        val fieldsValid = validateInput(name, price)
        if (fieldsValid) {
            _shopItem.value?.let {
                val item = it.copy(name = name, price = price)
                editShopItemUsageCase.editShopItem(item)
                finishWork()
            }

        }
    }

    fun getShopItem(shopItemId: Int) {
        val item = getShopItemUsageCase.getShopItem(shopItemId)
        _shopItem.value = item
    }

    private fun parsName(inputName: String?): String {
        return inputName?.trim() ?: ""
    }

    private fun parsPrice(inputPrice: String?): Int {
        return try {
            inputPrice?.trim()?.toInt() ?: 0
        } catch (e: NumberFormatException) {
            0
        }
    }

    private fun validateInput(name: String, price: Int): Boolean {
        var result = true

        if (name.isBlank()) {
            _errorInputName.value = true
            result = false
        }

        if (price <= 0) {
            _errorInputPrice.value = true
            result = false
        }
        return result
    }

    private fun resetErrorInputName() {
        _errorInputName.value = false
    }
    private fun resetErrorPriceName() {
        _errorInputPrice.value = false
    }

    private fun finishWork() {
        _shouldCloseScreen.value = Unit
    }
}