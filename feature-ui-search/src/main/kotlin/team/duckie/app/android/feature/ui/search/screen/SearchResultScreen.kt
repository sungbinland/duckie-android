/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.search.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import kotlinx.collections.immutable.toPersistentList
import org.orbitmvi.orbit.compose.collectAsState
import team.duckie.app.android.feature.ui.search.constants.SearchResultStep
import team.duckie.app.android.feature.ui.search.viewmodel.SearchViewModel
import team.duckie.app.android.shared.ui.compose.DuckExamSmallCover
import team.duckie.app.android.shared.ui.compose.DuckTestCoverItem
import team.duckie.app.android.shared.ui.compose.UserFollowingLayout
import team.duckie.app.android.util.compose.activityViewModel
import team.duckie.quackquack.ui.component.QuackMainTab
import team.duckie.quackquack.ui.component.QuackTopAppBar
import team.duckie.quackquack.ui.icon.QuackIcon

private val HomeTagListPadding = PaddingValues(
    top = 20.dp,
    start = 16.dp,
    end = 16.dp,
)

@Composable
internal fun SearchResultScreen(
    modifier: Modifier = Modifier,
    vm: SearchViewModel = activityViewModel(),
    onPrevious: () -> Unit,
) {
    val state = vm.collectAsState().value

    val searchUsers = vm.searchUsers.collectAsLazyPagingItems()
    val searchExams = vm.searchExams.collectAsLazyPagingItems()

    val tabTitles = SearchResultStep.values().map {
        it.title
    }.toPersistentList()

    LaunchedEffect(Unit) {
        vm.getRecentSearch()
        vm.fetchSearchExams(state.searchKeyword)
        vm.fetchSearchUsers(state.searchKeyword)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .nestedScroll(rememberNestedScrollInteropConnection()),
    ) {
        QuackTopAppBar(
            leadingIcon = QuackIcon.ArrowBack,
            onLeadingIconClick = {
                onPrevious()
            },
            leadingText = state.searchKeyword,
        )
        QuackMainTab(
            titles = tabTitles,
            selectedTabIndex = state.tagSelectedTab.index,
            onTabSelected = { index ->
                vm.updateSearchResultTab(SearchResultStep.toStep(index))
            },
        )
        when (state.tagSelectedTab) {
            SearchResultStep.DuckExam -> LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                state = rememberLazyGridState(),
                verticalArrangement = Arrangement.spacedBy(48.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = HomeTagListPadding,
            ) {
                items(searchExams.itemCount) { index ->
                    searchExams[index].let { exam ->
                        DuckExamSmallCover(
                            duckTestCoverItem = DuckTestCoverItem(
                                testId = exam?.id ?: 0,
                                thumbnailUrl = exam?.thumbnailUrl,
                                nickname = exam?.user?.nickname ?: "",
                                title = exam?.title ?: "",
                                solvedCount = exam?.solvedCount ?: 0,
                            ),
                            onItemClick = {
                                // TODO(limsaTehyun): 상세보기로 이동
                            },
                        )
                    }
                }
            }

            SearchResultStep.User -> LazyColumn(
                contentPadding = HomeTagListPadding,
            ) {
                items(searchUsers) { item ->
                    UserFollowingLayout(
                        userId = item?.userId ?: 0,
                        profileImgUrl = item?.profileImgUrl ?: "",
                        nickname = item?.nickname ?: "",
                        favoriteTag = item?.favoriteTag ?: "",
                        tier = item?.tier ?: "",
                        isFollowing = item?.isFollowing ?: false,
                        onClickFollow = { follow ->
                            vm.followUser(
                                userId = item?.userId ?: 0,
                                isFollowing = follow,
                            )
                        },
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(48.dp))
    }
}
