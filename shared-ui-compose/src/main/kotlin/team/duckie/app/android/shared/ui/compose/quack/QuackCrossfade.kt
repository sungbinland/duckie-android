/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.shared.ui.compose.quack

import androidx.compose.animation.Crossfade
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import team.duckie.quackquack.ui.animation.QuackAnimationSpec

@Composable
fun <T> QuackCrossfade(
    modifier: Modifier = Modifier,
    targetState: T,
    content: @Composable (T) -> Unit,
) {
    Crossfade(
        modifier = modifier,
        targetState = targetState,
        animationSpec = QuackAnimationSpec(),
        content = content,
    )
}
