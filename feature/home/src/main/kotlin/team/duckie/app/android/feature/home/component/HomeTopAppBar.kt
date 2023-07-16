/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.toImmutableList
import okhttp3.internal.immutableListOf
import team.duckie.app.android.common.compose.ui.TextTabLayout
import team.duckie.app.android.common.compose.ui.icon.v1.Create
import team.duckie.app.android.feature.home.R
import team.duckie.quackquack.material.QuackTypography
import team.duckie.quackquack.ui.component.QuackImage
import team.duckie.quackquack.ui.icon.QuackIcon
import team.duckie.quackquack.ui.util.DpSize
import team.duckie.quackquack.material.QuackColor as QuackV2Color

internal val HomeIconSize = DpSize(24.dp)

@Composable
internal fun HomeTopAppBar(
    modifier: Modifier = Modifier,
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit,
    onClickedCreate: () -> Unit,
) {
    val context = LocalContext.current

    val homeTextTabTitles = remember {
        immutableListOf(
            context.getString(R.string.recommend),
            context.getString(R.string.following),
        )
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        TextTabLayout(
            titles = homeTextTabTitles.toImmutableList(),
            selectedTabIndex = selectedTabIndex,
            onTabSelected = onTabSelected,
            tabStyle = QuackTypography.Title2.change(color = QuackV2Color.Gray2),
        )
        QuackImage(
            src = QuackIcon.Create,
            onClick = onClickedCreate,
            size = HomeIconSize,
        )
    }
}
