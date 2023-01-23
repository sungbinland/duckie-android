/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.solve.problem.common

import androidx.compose.foundation.layout.height
import androidx.compose.material.Slider
import androidx.compose.material.SliderColors
import androidx.compose.material.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import team.duckie.app.android.util.compose.rememberNoRippleInteractionSource
import team.duckie.quackquack.ui.color.QuackColor


@Composable
internal fun primarySliderColors() = SliderDefaults.colors(
    thumbColor = QuackColor.DuckieOrange.composeColor,
    activeTrackColor = QuackColor.DuckieOrange.composeColor,
    inactiveTrackColor = Color.Transparent,
)

@Composable
internal fun bufferSliderColors() = SliderDefaults.colors(
    disabledThumbColor = Color.Transparent,
    disabledActiveTrackColor = QuackColor.Gray2.composeColor,
    disabledInactiveTrackColor = QuackColor.Gray3.composeColor,
)

@Composable
internal fun BottomSlider(
    modifier: Modifier,
    enabled: Boolean = true,
    value: Float,
    onValueChanged: (Float) -> Unit,
    colors: SliderColors = primarySliderColors(),
    range: ClosedFloatingPointRange<Float> = 0f..100f,
) {
    val interactionSource = rememberNoRippleInteractionSource()
    Slider(
        modifier = modifier,
        value = value,
        enabled = enabled,
        onValueChange = onValueChanged,
        colors = colors,
        valueRange = range,
        interactionSource = interactionSource,
    )
}