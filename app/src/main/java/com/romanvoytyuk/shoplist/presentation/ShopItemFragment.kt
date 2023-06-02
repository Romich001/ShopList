package com.romanvoytyuk.shoplist.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputLayout
import com.romanvoytyuk.shoplist.R
import com.romanvoytyuk.shoplist.domain.ShopItem

class ShopItemFragment(
    private val screenMode: String = UNKNOWN_MODE,
    private val shopItemId: Int = ShopItem.UNDEFINED_ID
) : Fragment() {

    private lateinit var viewModel: ShopItemViewModel

    private lateinit var tilName: TextInputLayout
    private lateinit var tilPrice: TextInputLayout
    private lateinit var etName: EditText
    private lateinit var etPrice: EditText
    private lateinit var buttonSave: Button


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_shop_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parsParams()
        initViews(view)
        viewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]
        observeViewModel()
        setTextChangeListeners()
        setScreenMode()
    }

    private fun setScreenMode() {
        when (screenMode) {
            MODE_EDIT -> launchEditMode()
            MODE_ADD -> launchAddMode()
        }
    }

    private fun setTextChangeListeners() {
        etName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputName()
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
        etPrice.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputPrice()
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
    }

    private fun  observeViewModel() {
        viewModel.shouldCloseScreen.observe(viewLifecycleOwner) {
            activity?.onBackPressed()
        }
        viewModel.errorInputName.observe(viewLifecycleOwner) {
            tilName.error = if (it) "Wrong name" else null
        }
        viewModel.errorInputPrice.observe(viewLifecycleOwner) {
            tilPrice.error = if (it) "Wrong price" else null
        }
    }

    private fun launchAddMode() {
        buttonSave.setOnClickListener {
            viewModel.addShopItem(
                etName.text.toString(), etPrice.text.toString()
            )
        }
    }

    private fun launchEditMode() {
        viewModel.getShopItem(shopItemId)
        viewModel.shopItem.observe(viewLifecycleOwner) {
            etName.setText(it.name)
            etPrice.setText(it.price.toString())
        }
        buttonSave.setOnClickListener {
            viewModel.editShopItem(
                etName.text.toString(), etPrice.text.toString()
            )
        }

    }

    private fun parsParams() {
        if (screenMode != MODE_ADD && screenMode != MODE_EDIT) {
            throw RuntimeException("Unknown screen mode $screenMode")
        }
        if (screenMode == MODE_EDIT && shopItemId == ShopItem.UNDEFINED_ID) {
            throw RuntimeException("Wrong shop item id")
        }


    }

    private fun initViews(view: View) {
        tilName = view.findViewById(R.id.item_name_input_layout)
        tilPrice = view.findViewById(R.id.item_price_input_layout)
        etName = view.findViewById(R.id.item_name_input_field)
        etPrice = view.findViewById(R.id.item_price_input_field)
        buttonSave = view.findViewById(R.id.buttonSave)
    }


    companion object {
        private const val EXTRA_SCREEN_MODE = "extra_mode"
        private const val MODE_EDIT = "edit_mode"
        private const val MODE_ADD = "add_mode"
        private const val EXTRA_SHOP_ITEM_ID = "extra_shop_item_id"
        private const val UNKNOWN_MODE = ""

        fun newInstanceAddItem() : ShopItemFragment {
            return ShopItemFragment(MODE_ADD)
        }

        fun newInstanceEditItem(shopItemId: Int) : ShopItemFragment {
            return ShopItemFragment(MODE_EDIT, shopItemId)
        }


        fun newIntentAddItem(context: Context): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_ADD)
            return intent
        }


        fun newIntentEditItem(context: Context, shopItemId: Int): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_EDIT)
            intent.putExtra(EXTRA_SHOP_ITEM_ID, shopItemId)
            return intent
        }


    }
}