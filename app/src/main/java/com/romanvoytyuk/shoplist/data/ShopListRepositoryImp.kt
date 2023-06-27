package com.romanvoytyuk.shoplist.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.romanvoytyuk.shoplist.domain.ShopItem
import com.romanvoytyuk.shoplist.domain.ShopListRepository

class ShopListRepositoryImp(
    application: Application
) : ShopListRepository {

    private val shopListDao = AppDataBase.getInstance(application).getShopListDao()
    private val mapper = ShopListMapper()


    override fun getShopItem(id: Int): ShopItem {
        return mapper.mapShopItemDbToEntity(
            shopListDao.getShopItem(id)
        )
    }


    override fun deleteShopItem(shopItem: ShopItem) {
        shopListDao.deleteShopItem(shopItem.id)
    }

    override fun editShopItem(shopItem: ShopItem) {
        shopListDao.addShopItem(mapper.mapEntityToShopItemDb(shopItem))
    }

    override fun getShopItemList(): LiveData<List<ShopItem>> = shopListDao.getShopItemList().map {
        mapper.mapListShopListDbToListShopItem(it)

    }

    override fun addShopItem(shopItem: ShopItem) {
        shopListDao.addShopItem(mapper.mapEntityToShopItemDb(shopItem))
    }

}