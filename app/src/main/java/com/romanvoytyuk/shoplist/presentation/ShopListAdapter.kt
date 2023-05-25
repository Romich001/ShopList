package com.romanvoytyuk.shoplist.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.romanvoytyuk.shoplist.R
import com.romanvoytyuk.shoplist.domain.ShopItem

class ShopListAdapter : RecyclerView.Adapter<ShopListAdapter.ShopItemViewHolder>() {

    var shopList = listOf<ShopItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    var shopItemLongClick: ((ShopItem) -> Unit)? = null
    var shopItemClick: ((ShopItem) -> Unit)? = null


    class ShopItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val tvName = view.findViewById<TextView>(R.id.tv_name)
        val tvCount = view.findViewById<TextView>(R.id.tv_count)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        return ShopItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(viewType, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return shopList.size
    }

    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        val shopItem = shopList[position]

        holder.tvName.text = shopItem.name
        holder.tvCount.text = shopItem.price.toString()
        holder.itemView.setOnLongClickListener {
            shopItemLongClick?.invoke(shopItem)
            true
        }
        holder.itemView.setOnClickListener {
            shopItemClick?.invoke(shopItem)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (shopList[position].enable) {
            R.layout.enable_shop_item
        } else {
            R.layout.disenable_shop_item
        }
    }

    companion object {
        const val MAX_POOL_SIZE = 15
    }
}