package com.shubhans.pageflipanimation.data

import androidx.annotation.DrawableRes

data class Headline(
    val title: String,
    val description: String,
    val category: String,
    @DrawableRes val imageRes: Int
)