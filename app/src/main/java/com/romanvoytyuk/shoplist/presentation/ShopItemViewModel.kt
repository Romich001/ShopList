package com.romanvoytyuk.shoplist.presentation

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

    fun addShopItem(inputName: String?, inputPrice: String?) {
        val name = parsName(inputName)
        val price = parsPrice(inputPrice)
        val fieldsValid = validateInput(name, price)
        if (fieldsValid) {
            val shopItem = ShopItem(name, price, true)
            addShopItemUsageCase.addShopItem(shopItem)

        }

    }

    fun editShopItem(inputName: String?, inputPrice: String?) {
        val name = parsName(inputName)
        val price = parsPrice(inputPrice)
        val fieldsValid = validateInput(name, price)
        if (fieldsValid) {
            val shopItem = ShopItem(name, price, true)
            editShopItemUsageCase.editShopItem(shopItem)

        }
    }

    fun getShopItem(shopItemId: Int) {
        val item = getShopItemUsageCase.getShopItem(shopItemId)
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
            //TODO: show error input name
            result = false
        }

        if (price <= 0) {
            //TODO: show error input price
            result = false
        }
        return result
    }
}