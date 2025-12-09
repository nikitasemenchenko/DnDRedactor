package com.example.dndredactor.data

import androidx.annotation.StringRes

class CustomException(
    @StringRes val stringResId: Int,
    message: String? = null,
    cause: Throwable? = null
) : Exception(message, cause)