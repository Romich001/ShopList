package com.romanvoytyuk.shoplist.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.SimpleCallback
import androidx.recyclerview.widget.RecyclerView
import com.romanvoytyuk.shoplist.R
import com.romanvoytyuk.shoplist.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), ShopItemFragment.OnEditingFinishedListener {

    private lateinit var viewModel: MainViewModel
    private lateinit var shopListAdapter: ShopListAdapter
    private var fragmentContainer: FragmentContainerView? = null
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setupRecyclerView()
        fragmentContainer = binding.shopItemContainer
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.listOfShopItem.observe(this) {
            shopListAdapter.submitList(it)
        }
        addButtonOnClick()

    }

    private fun addButtonOnClick() {

        binding.buttonAddShopItem.setOnClickListener {
            if (isOnOnePane()) {
                val intent = ShopItemActivity.newIntentAddItem(this)
                startActivity(intent)
            } else {
                launchFragment(ShopItemFragment.newInstanceAddItem())
            }

        }
    }

    override fun onEditingFinished() {
        Toast.makeText(this, "Success", Toast.LENGTH_LONG).show()
        supportFragmentManager.popBackStack()
    }

    private fun launchFragment(fragment: ShopItemFragment) {
        supportFragmentManager.apply {
            popBackStack()
            beginTransaction()
                .replace(R.id.shop_item_container, fragment)
                .addToBackStack(null)
                .commit()
        }

    }

    private fun isOnOnePane(): Boolean {
        return fragmentContainer == null
    }

    private fun setupRecyclerView() {

        val rv = binding.rvShopList
        with(rv) {
            shopListAdapter = ShopListAdapter()
            adapter = shopListAdapter
            recycledViewPool.setMaxRecycledViews(
                R.layout.enable_shop_item,
                ShopListAdapter.MAX_POOL_SIZE
            )
            recycledViewPool.setMaxRecycledViews(
                R.layout.disenable_shop_item,
                ShopListAdapter.MAX_POOL_SIZE
            )

        }
        setupLongClickListener()
        setupClickListener()
        setupSwipeListener(rv)

    }

    private fun setupSwipeListener(rv: RecyclerView?) {
        val itemTouchHelper = ItemTouchHelper(object : SimpleCallback(
            0,
            ItemTouchHelper.START or ItemTouchHelper.END
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val shopItem = shopListAdapter.currentList[position]
                viewModel.deleteShopItem(shopItem)

            }

        })
        itemTouchHelper.attachToRecyclerView(rv)
    }

    private fun setupClickListener() {
        shopListAdapter.shopItemClick = {
            if (isOnOnePane()) {
                val intent = ShopItemActivity.newIntentEditItem(this, it.id)
                startActivity(intent)
            } else {
                launchFragment(ShopItemFragment.newInstanceEditItem(it.id))
            }

        }
    }

    private fun setupLongClickListener() {
        shopListAdapter.shopItemLongClick = {
            viewModel.changeEnableState(it)
        }
    }

}