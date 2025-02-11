package com.shubhans.pageflipanimation.ui.flip

import androidx.compose.ui.graphics.Shape

sealed class PageFlipType(val shape: Shape) {
    data object Top : PageFlipType(TopShape)
    data object Bottom : PageFlipType(BottomShape)
    data object Left : PageFlipType(LeftShape)
    data object Right : PageFlipType(RightShape)
}