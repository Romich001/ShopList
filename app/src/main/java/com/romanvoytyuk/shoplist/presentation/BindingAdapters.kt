package com.romanvoytyuk.shoplist.presentation

import androidx.databinding.BindingAdapter

@BindingAdapter("setErrorMine", "setErrorText")
fun bindError(
    tf: com.google.android.material.textfield.TextInputLayout,
    status: Boolean,
    text: String
) {
    tf.error = if (status) text else null
}

