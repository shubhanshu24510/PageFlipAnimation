package com.shubhans.pageflipanimation.ui.flip

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import kotlin.math.absoluteValue
import kotlin.math.max
import kotlin.math.min

@Composable
fun BoxScope.PageFlap(
    page: Int,
    state: PagerState,
    pageFlap: PageFlipType,
    imageBitmap: () -> ImageBitmap?,
    modifier: Modifier = Modifier,
    animatedOverScrollAmount: () -> Float = { 0f }
) {
    val density = LocalDensity.current
    val size by remember {
        derivedStateOf {
            imageBitmap()?.let {
                with(density) {
                    DpSize(it.width.toDp(), it.height.toDp())
                }
            } ?: DpSize.Zero
        }
    }
    Canvas(
        modifier
            .size(size)
            .align(Alignment.TopStart)
            .graphicsLayer {
                shape = pageFlap.shape
                clip = true

                cameraDistance = 65f
                when (pageFlap) {
                    is PageFlipType.Bottom -> {
                        rotationX = max(
                            (state.startOffsetForPage(page) * 180f).coerceIn(0f..90f),
                            animatedOverScrollAmount().coerceAtMost(0f) * -20f
                        )
                    }

                    is PageFlipType.Left -> {
                        rotationY = -min(
                            (state.endOffsetForPage(page) * 180f).coerceIn(-90f..0f),
                            animatedOverScrollAmount().coerceAtLeast(0f) * -20f
                        )
                    }

                    is PageFlipType.Right -> {
                        rotationY = -max(
                            (state.startOffsetForPage(page) * 180f).coerceIn(0f..90f),
                            animatedOverScrollAmount().coerceAtMost(0f) * -20f
                        )
                    }

                    is PageFlipType.Top -> {
                        rotationX = min(
                            (state.endOffsetForPage(page) * 180f).coerceIn(-90f..0f),
                            animatedOverScrollAmount().coerceAtLeast(0f) * -20f
                        )
                    }
                }
            }
    ) {
        imageBitmap()?.let { imageBitmap ->
            drawImage(imageBitmap)
            drawImage(
                imageBitmap,
                colorFilter = ColorFilter.tint(
                    Color.Black.copy(
                        alpha = when (pageFlap) {
                            PageFlipType.Top, PageFlipType.Left -> max(
                                (state.endOffsetForPage(page).absoluteValue * .9f).coerceIn(
                                    0f..1f
                                ), animatedOverScrollAmount() * .3f
                            )

                            PageFlipType.Bottom, PageFlipType.Right -> max(
                                (state.startOffsetForPage(page) * .9f).coerceIn(
                                    0f..1f
                                ), (animatedOverScrollAmount() * -1) * .3f
                            )
                        },
                    )
                )
            )
        }
    }
}


