package com.shubhans.pageflipanimation.ui.flip

sealed class FlipPagerOrientation {
    object Horizontal : FlipPagerOrientation()
    object Vertical : FlipPagerOrientation()
}