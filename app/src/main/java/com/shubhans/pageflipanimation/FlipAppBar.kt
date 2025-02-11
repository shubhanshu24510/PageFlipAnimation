package com.shubhans.pageflipanimation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.togetherWith
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.TransformOrigin
import com.shubhans.pageflipanimation.ui.flip.FlipPagerOrientation

@Composable
fun ColumnScope.FlipAppBar(
    modifier: Modifier = Modifier,
    orientation: FlipPagerOrientation,
    darkMode: Boolean,
    onDarkModeChange: (Boolean) -> Unit,
    onOrientationChange: (FlipPagerOrientation) -> Unit
) {
    Row(
        modifier = Modifier
            .align(Alignment.CenterHorizontally)
            .widthIn(max = 480.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = R.string.news_App),
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(16.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        Row {
            IconButton(onClick = {
                onDarkModeChange(!darkMode)
            }) {
                AnimatedContent(
                    targetState = darkMode,
                    transitionSpec = {
                        scaleIn(
                            transformOrigin = TransformOrigin(1f, 0.5f)
                        ) togetherWith scaleOut(
                            transformOrigin = TransformOrigin(0f, 0.5f)
                        ) using SizeTransform(clip = false)
                    }) {
                    Icon(
                        painter = if (it) painterResource(id = R.drawable.ic_light_mode) else painterResource(
                            id = R.drawable.ic_dark_mode
                        ),
                        contentDescription = null
                    )
                }
            }
            IconButton(onClick = {
                onOrientationChange(
                    when (orientation) {
                        FlipPagerOrientation.Vertical -> FlipPagerOrientation.Vertical
                        FlipPagerOrientation.Horizontal -> FlipPagerOrientation.Horizontal
                    }
                )
            }) {
                val rotation = animateFloatAsState(
                    targetValue = when (orientation) {
                        FlipPagerOrientation.Vertical -> 0f
                        FlipPagerOrientation.Horizontal -> 90f
                    },
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_flip_orientation),
                    contentDescription = null,
                    modifier = Modifier.rotate(rotation.value)
                )
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun ColumnScope.FlipAppBarPreview() {
    FlipAppBar(
        orientation = FlipPagerOrientation.Vertical,
        darkMode = true,
        onDarkModeChange = {},
        onOrientationChange = {}
    )
}
