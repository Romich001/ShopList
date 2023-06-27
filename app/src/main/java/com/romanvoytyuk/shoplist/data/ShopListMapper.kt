package com.romanvoytyuk.shoplist.data

import com.romanvoytyuk.shoplist.domain.ShopItem

class ShopListMapper {

    fun mapEntityToShopItemDb(shopItem: ShopItem) = ShopItemDbModel(
        id = shopItem.id,
        name = shopItem.name,
        price = shopItem.price,
        enable = shopItem.enable
    )

    fun mapShopItemDbToEntity(shopItemDb: ShopItemDbModel) = ShopItem(
        id = shopItemDb.id,
        name = shopItemDb.name,
        price = shopItemDb.price,
        enable = shopItemDb.enable
    )

    fun mapListShopListDbToListShopItem(list: List<ShopItemDbModel>) : List<ShopItem> {
        return list.map { mapShopItemDbToEntity(it) }
    }
}