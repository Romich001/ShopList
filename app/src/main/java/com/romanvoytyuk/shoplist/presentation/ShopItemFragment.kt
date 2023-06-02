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
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputLayout
import com.romanvoytyuk.shoplist.R
import com.romanvoytyuk.shoplist.domain.ShopItem

class ShopItemFragment(

) : Fragment() {

    private lateinit var viewModel: ShopItemViewModel

    private lateinit var tilName: TextInputLayout
    private lateinit var tilPrice: TextInputLayout
    private lateinit var etName: EditText
    private lateinit var etPrice: EditText
    private lateinit var buttonSave: Button

    private var screenMode: String = UNKNOWN_MODE
    private var shopItemId: Int = ShopItem.UNDEFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parsParams()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_shop_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

    private fun observeViewModel() {
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
        val args = requireArguments()
        if (!args.containsKey(SCREEN_MODE)) {
            throw RuntimeException("No SCREEN_MODE presents")
        }
        val mode = args.getString(SCREEN_MODE)
        if (mode != MODE_ADD && mode != MODE_EDIT) {
            throw RuntimeException("Unknown screen mode $screenMode")
        }
        screenMode = mode
        if (screenMode == MODE_EDIT) {

            if (!args.containsKey(SHOP_ITEM_ID)) {
                throw RuntimeException("Param shop item id is absent")
            }
            shopItemId = args.getInt(SHOP_ITEM_ID, ShopItem.UNDEFINED_ID)
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
        private const val SCREEN_MODE = "extra_mode"
        private const val MODE_EDIT = "edit_mode"
        private const val MODE_ADD = "add_mode"
        private const val SHOP_ITEM_ID = "extra_shop_item_id"
        private const val UNKNOWN_MODE = ""

        fun newInstanceAddItem(): ShopItemFragment {
            return ShopItemFragment().apply {
                arguments = Bundle().apply { putString(SCREEN_MODE, MODE_ADD) }
            }

        }

        fun newInstanceEditItem(shopItemId: Int): ShopItemFragment {
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, MODE_EDIT)
                    putInt(SHOP_ITEM_ID, shopItemId)
                }
            }
        }


    }
}