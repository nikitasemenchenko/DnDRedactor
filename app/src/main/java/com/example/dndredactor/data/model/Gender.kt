package com.example.dndredactor.data.model

import androidx.annotation.StringRes
import com.example.dndredactor.R

enum class Gender(
    @StringRes val titleRes: Int
) {
    MALE(R.string.male),
    FEMALE(R.string.female),
    UNSPECIFIED(R.string.not_specified)
}