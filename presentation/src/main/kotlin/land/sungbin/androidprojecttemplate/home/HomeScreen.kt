package land.sungbin.androidprojecttemplate.home

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.LocalAbsoluteElevation
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch
import land.sungbin.androidprojecttemplate.R
import land.sungbin.androidprojecttemplate.common.component.DuckieFab
import land.sungbin.androidprojecttemplate.common.UiStatus
import land.sungbin.androidprojecttemplate.common.component.DuckieLoadingIndicator
import land.sungbin.androidprojecttemplate.domain.model.Feed
import land.sungbin.androidprojecttemplate.domain.model.constraint.FeedType
import land.sungbin.androidprojecttemplate.home.component.DrawerContent
import land.sungbin.androidprojecttemplate.home.component.DuckDealFeed
import land.sungbin.androidprojecttemplate.home.component.FeedHeader
import land.sungbin.androidprojecttemplate.home.component.NormalFeed
import land.sungbin.androidprojecttemplate.home.dto.toDuckDealFeed
import land.sungbin.androidprojecttemplate.home.dto.toNormalFeed
import team.duckie.quackquack.ui.component.QuackBottomSheetItem
import team.duckie.quackquack.ui.component.QuackHeadlineBottomSheet
import team.duckie.quackquack.ui.component.QuackImage
import team.duckie.quackquack.ui.component.QuackMenuFabItem
import team.duckie.quackquack.ui.component.QuackModalDrawer
import team.duckie.quackquack.ui.component.QuackSimpleBottomSheet
import team.duckie.quackquack.ui.component.QuackTopAppBar
import team.duckie.quackquack.ui.component.rememberQuackDrawerState
import team.duckie.quackquack.ui.icon.QuackIcon

@OptIn(
    ExperimentalMaterialApi::class,
    ExperimentalLifecycleComposeApi::class,
)
@Composable
internal fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val homeState by viewModel.container.stateFlow.collectAsStateWithLifecycle()
    val coroutineScope = rememberCoroutineScope()
    val drawerState = rememberQuackDrawerState()
    val homeBottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val moreBottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)

    QuackModalDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent()
        }
    ) {
        QuackHeadlineBottomSheet(
            bottomSheetState = homeBottomSheetState,
            headline = stringResource(id = R.string.feed_filtering_title),
            items = homeState.filterBottomSheetItems.toPersistentList(),
            onClick = { bottomSheetItem: QuackBottomSheetItem ->
                viewModel.selectFilterBottomSheet(bottomSheetItem)
            }
        ) {
            QuackSimpleBottomSheet(
                bottomSheetState = moreBottomSheetState,
                items = homeState.moreBottomSheetItems.toPersistentList(),
                onClick = { bottomSheetItem: QuackBottomSheetItem ->
                    viewModel.selectMoreBottomSheet(bottomSheetItem)
                }
            ) {
                HomeContent(
                    feeds = homeState.feeds,
                    itemStatus = homeState.itemStatus,
                    interestedTags = homeState.interestedTags,
                    onClickLeadingIcon = {
                        coroutineScope.launch { drawerState.open() }
                    },
                    onClickTrailingIcon = {
                        coroutineScope.launch { homeBottomSheetState.show() }
                    },
                    onClickMoreIcon = { selectedUser: String ->
                        viewModel.changeSelectedUser(selectedUser)
                        coroutineScope.launch { moreBottomSheetState.show() }
                    },
                    onClickTag = viewModel::deleteTag,
                    onFabMenuClick = viewModel::onFabMenuClick,
                    onRefresh = viewModel::refresh,
                )
            }
        }
        viewModel.container.sideEffectFlow.run {
            LaunchedEffect(key1 = this) {
                collect { sideEffect ->
                    when (sideEffect) {
                        HomeSideEffect.NavigateToWriteFeed -> {

                        }

                        HomeSideEffect.NavigateToWriteDuckDeal -> {

                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun HomeContentByStatus(
    status: UiStatus,
    success: @Composable () -> Unit,
    failed: @Composable () -> Unit,
    loading: @Composable () -> Unit,
) = when (status) {
    UiStatus.Success -> success()

    UiStatus.Loading -> loading()

    is UiStatus.Failed -> failed()
}

@Composable
fun HomeContent(
    itemStatus: UiStatus,
    feeds: List<Feed>,
    interestedTags: List<String>,
    onClickLeadingIcon: () -> Unit,
    onClickTrailingIcon: () -> Unit,
    onClickMoreIcon: (user: String) -> Unit,
    onClickTag: (index: Int) -> Unit,
    onFabMenuClick: (index: Int) -> Unit,
    onRefresh: () -> Unit,
) {
    /*val commentCount by remember { mutableStateOf(0) }
    var isLike by remember { mutableStateOf(false) }
    var likeCount by remember { mutableStateOf(12000) }*/
    var fabExpanded by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        QuackTopAppBar(
            leadingIcon = QuackIcon.Profile,
            onClickLeadingIcon = onClickLeadingIcon,
            centerContent = {
                QuackImage(
                    src = R.drawable.top_bar_logo,
                    overrideSize = DuckieLogoSize,
                )
            },
            trailingIcon = QuackIcon.Filter,
            onClickTrailingIcon = onClickTrailingIcon,
        )
        HomeContentByStatus(
            status = itemStatus,
            success = {
                LazyFeedColumn(
                    feeds = feeds,
                    header = {
                        FeedHeader(
                            tagItems = interestedTags,
                            onTagClick = onClickTag
                        )
                    },
                    onRefresh = onRefresh,
                )
            },
            loading = {
                Crossfade(targetState = itemStatus) {
                    DuckieLoadingIndicator()
                }
            },
            failed = { }
        )
    }
    DuckieFab(
        items = homeFabMenuItems(),
        expanded = fabExpanded,
        onFabClick = {
            fabExpanded = !fabExpanded
        },
        onItemClick = { index, _ ->
            onFabMenuClick(index)
        },
        paddingValues = homeFabPadding,
    )
}

@Composable
fun LazyFeedColumn(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(
        vertical = 8.dp,
        horizontal = 16.dp
    ),
    verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(space = 28.dp),
    header: @Composable () -> Unit,
    feeds: List<Feed>,
    onRefresh: () -> Unit,
) {
    val swipeRefreshState = rememberSwipeRefreshState(false)
    SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = onRefresh,
    ) {
        LazyColumn(
            modifier = modifier,
            contentPadding = contentPadding,
            verticalArrangement = verticalArrangement
        ) {
            item {
                header()
            }
            items(
                items = feeds,
                key = { feed: Feed ->
                    feed.id
                }
            ) { feed: Feed -> // //DuckDeal 이므로 null이 아님을 보장
                when (feed.type) {
                    FeedType.Normal -> {
                        NormalFeed(feed.toNormalFeed())
                    }

                    FeedType.DuckDeal -> {
                        DuckDealFeed(feed.toDuckDealFeed())
                    }
                }
            }
        }
    }

}


@Stable
internal val DuckieLogoSize = DpSize(
    width = 72.dp,
    height = 24.dp,
)

@Stable
internal val homeFabPadding = PaddingValues(
    bottom = 12.dp,
    end = 16.dp
)

private const val FeedIndex = 1
private const val DuckDealIndex = 2

@Stable
@Composable
internal fun homeFabMenuItems(): PersistentList<QuackMenuFabItem> {
    return persistentListOf(
        QuackMenuFabItem(
            icon = QuackIcon.Feed,
            text = stringResource(id = R.string.feed),
        ),
        QuackMenuFabItem(
            icon = QuackIcon.Buy,
            text = stringResource(id = R.string.duck_deal),
        )
    )
}

@Stable
@Composable
internal fun homeFilterBottomSheetItems(): PersistentList<QuackBottomSheetItem> {
    return persistentListOf(
        QuackBottomSheetItem(
            title = stringResource(id = R.string.feed_filtering_both_feed_duck_deal),
            isImportant = false,
        ),
        QuackBottomSheetItem(
            title = stringResource(id = R.string.feed_filtering_feed),
            isImportant = true,
        ),
        QuackBottomSheetItem(
            title = stringResource(id = R.string.feed_filtering_duck_deal),
            isImportant = false,
        )
    )
}

@Stable
@Composable
internal fun MoreBottomSheetItems(selectedUser: String): PersistentList<QuackBottomSheetItem> {
    return persistentListOf(
        QuackBottomSheetItem(
            title = stringResource(R.string.follow_other, selectedUser),
            isImportant = false,
        ),
        QuackBottomSheetItem(
            title = stringResource(R.string.blocking_other_feed, selectedUser),
            isImportant = false,
        ),
        QuackBottomSheetItem(
            title = stringResource(R.string.report),
            isImportant = false,
        )
    )
}