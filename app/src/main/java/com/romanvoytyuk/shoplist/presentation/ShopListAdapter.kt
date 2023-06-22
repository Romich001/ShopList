package com.romanvoytyuk.shoplist.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.romanvoytyuk.shoplist.R
import com.romanvoytyuk.shoplist.databinding.DisenableShopItemBinding
import com.romanvoytyuk.shoplist.databinding.EnableShopItemBinding
import com.romanvoytyuk.shoplist.domain.ShopItem

class ShopListAdapter :
    ListAdapter<ShopItem, ShopListAdapter.ShopItemViewHolder>(ShopItemDiffCallback()) {

    var shopItemLongClick: ((ShopItem) -> Unit)? = null
    var shopItemClick: ((ShopItem) -> Unit)? = null


    class ShopItemViewHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        return ShopItemViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                viewType,
                parent,
                false
            )
        )

    }


    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        val shopItem = currentList[position]
        val binding = holder.binding
        binding.root.setOnLongClickListener {
            shopItemLongClick?.invoke(shopItem)
            true
        }
        binding.root.setOnClickListener {
            shopItemClick?.invoke(shopItem)
        }

        when(binding) {
            is DisenableShopItemBinding -> {
                binding.tvName.text = shopItem.name
                binding.tvCount.text = shopItem.price.toString()
            }
            is EnableShopItemBinding -> {
                binding.tvName.text = shopItem.name
                binding.tvCount.text = shopItem.price.toString()
            }
        }


    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position).enable) {
            R.layout.enable_shop_item
        } else {
            R.layout.disenable_shop_item
        }
    }

    companion object {
        const val MAX_POOL_SIZE = 15
    }
}