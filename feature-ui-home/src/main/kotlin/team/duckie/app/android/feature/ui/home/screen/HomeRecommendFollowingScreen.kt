/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.home.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import org.orbitmvi.orbit.compose.collectAsState
import team.duckie.app.android.feature.ui.home.R
import team.duckie.app.android.feature.ui.home.component.HomeTopAppBar
import team.duckie.app.android.feature.ui.home.constants.HomeStep
import team.duckie.app.android.feature.ui.home.viewmodel.HomeViewModel
import team.duckie.app.android.feature.ui.home.viewmodel.state.HomeState
import team.duckie.app.android.shared.ui.compose.QuackMaxWidthDivider
import team.duckie.app.android.shared.ui.compose.UserFollowingLayout
import team.duckie.app.android.util.compose.activityViewModel
import team.duckie.app.android.util.kotlin.fastForEach
import team.duckie.quackquack.ui.component.QuackDivider
import team.duckie.quackquack.ui.component.QuackHeadLine2
import team.duckie.quackquack.ui.component.QuackSubtitle
import team.duckie.quackquack.ui.component.QuackTitle2

@Composable
internal fun HomeRecommendFollowingScreen(
    modifier: Modifier = Modifier,
    vm: HomeViewModel = activityViewModel(),
) {
    val state = vm.collectAsState().value

    LaunchedEffect(Unit) {
        vm.fetchRecommendFollowing()
    }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
    ) {
        item {
            HomeTopAppBar(
                selectedTabIndex = state.homeSelectedIndex.index,
                onTabSelected = { step ->
                    vm.changedHomeScreen(HomeStep.toStep(step))
                },
                onClickedCreate = {
                    vm.navigateToCreateProblem()
                },
            )
        }

        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(213.dp),
                contentAlignment = Alignment.Center,
            ) {
                QuackSubtitle(
                    text = stringResource(id = R.string.home_following_initial_title),
                    align = TextAlign.Center,
                )
            }
        }

        items(
            items = state.recommendFollowing,
        ) { categories ->
            HomeFollowingInitialRecommendUsers(
                modifier = Modifier.padding(bottom = 16.dp),
                topic = categories.topic,
                recommendUser = categories.users,
                onClickFollowing = { userId, isFollowing ->
                    vm.followUser(userId, isFollowing)
                },
            )
        }
    }
}

@Composable
private fun HomeFollowingInitialRecommendUsers(
    modifier: Modifier = Modifier,
    topic: String,
    recommendUser: ImmutableList<HomeState.RecommendUserByTopic.User>,
    onClickFollowing: (Int, Boolean) -> Unit,
) {
    Column(
        modifier = modifier,
    ) {
        QuackMaxWidthDivider()
        QuackTitle2(
            modifier = Modifier.padding(
                top = 24.dp,
                bottom = 12.dp,
            ),
            text = topic,
        )
        recommendUser.fastForEach { user ->
            UserFollowingLayout(
                userId = user.userId,
                profileImgUrl = user.profileImgUrl,
                nickname = user.nickname,
                favoriteTag = user.favoriteTag,
                tier = user.tier,
                isFollowing = user.isFollowing,
                onClickFollow = {
                    onClickFollowing(user.userId, it)
                },
            )
        }
    }
}
