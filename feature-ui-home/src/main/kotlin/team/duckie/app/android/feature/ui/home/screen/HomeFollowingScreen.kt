package team.duckie.app.android.feature.ui.home.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.QuackBody2
import team.duckie.quackquack.ui.component.QuackBody3
import team.duckie.quackquack.ui.component.QuackDivider
import team.duckie.quackquack.ui.component.QuackHeadLine2
import team.duckie.quackquack.ui.component.QuackImage
import team.duckie.quackquack.ui.component.QuackSubtitle2
import team.duckie.quackquack.ui.component.QuackTitle2
import team.duckie.quackquack.ui.modifier.quackClickable
import team.duckie.quackquack.ui.util.DpSize

data class Maker(
    val cover: String = "https://user-images.githubusercontent.com/80076029/206894333-d060111d-e78e-4294-8686-908b2c662f19.png",
    val profile: String,
    val title: String,
    val name: String,
    val takers: Int,
    val createAt: String,
)

internal val fakeFollowingTest = persistentListOf(
    Maker(
        profile = "https://www.pngitem.com/pimgs/m/80-800194_transparent-users-icon-png-flat-user-icon-png.png",
        title = "제 1회 도로 패션영역",
        name = "닉네임",
        takers = 30,
        createAt = "1일 전",
    ),
    Maker(
        profile = "https://www.pngitem.com/pimgs/m/80-800194_transparent-users-icon-png-flat-user-icon-png.png",
        title = "제 1회 도로 패션영역",
        name = "닉네임",
        takers = 30,
        createAt = "1일 전",
    ),
    Maker(
        profile = "https://www.pngitem.com/pimgs/m/80-800194_transparent-users-icon-png-flat-user-icon-png.png",
        title = "제 1회 도로 패션영역",
        name = "닉네임",
        takers = 30,
        createAt = "1일 전",
    ),
)

data class RecommendCategories(
    val topic: String,
    val users: PersistentList<RecommendUser>,
)

data class RecommendUser(
    val userId: Int = 0,
    val profile: String,
    val name: String,
    val taker: Int,
    val createAt: String,
)

private val HomeFollowingPadding: Dp = 24.dp

@Composable
internal fun HomeFollowingScreen(
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(HomeFollowingPadding)
    ) {
        itemsIndexed(fakeFollowingTest) { index, maker ->
            TestCoverWithMaker(
                profile = maker.profile,
                title = maker.title,
                name = maker.name,
                takers = maker.takers,
                createAt = maker.createAt,
                onClickUserProfile = {},
                onClickTestCover = {},
                cover = maker.cover,
            )

            if (index == fakeFollowingTest.size - 1) {
                Spacer(modifier = Modifier.height(HomeFollowingPadding))
            }
        }
    }
}

@Composable
internal fun HomeFollowingInitialScreen(
    modifier: Modifier = Modifier,
    recommendCategories: PersistentList<RecommendCategories>,
) {
    LazyColumn(
        modifier = modifier,
    ) {
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(164.dp),
                contentAlignment = Alignment.Center,
            ) {
                QuackHeadLine2(
                    text = "마음에 드는 출제자를 팔로우하면\n최신 문제지를 빠르게 풀어볼 수 있어요!",
                    align = TextAlign.Center,
                )
            }
        }

        items(recommendCategories) { categories ->
            HomeRecommendUsers(
                topic = categories.topic,
                recommendUser = categories.users,
                onClickFollowing = {

                }
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun HomeRecommendUsers(
    topic: String,
    recommendUser: List<RecommendUser>,
    onClickFollowing: (Int) -> Unit,
) {
    QuackDivider()

    Spacer(modifier = Modifier.height(24.dp))

    QuackTitle2(
        text = topic,
    )

    Spacer(modifier = Modifier.height(12.dp))

    recommendUser.map { user ->
        var following by remember { mutableStateOf(false) }

        RecommendUserProfile(
            profile = user.profile,
            name = user.name,
            takers = user.taker,
            createAt = user.createAt,
            isFollowing = following,
            onClickFollowing = {
                following = !following
                onClickFollowing(user.userId)
            },
        )
    }

    Spacer(modifier = Modifier.height(16.dp))
}

private val HomeProfileSize: DpSize = DpSize(
    all = 24.dp,
)

// TODO("임의의 값, figma에 명시 X")
private val HomeProfileShape: RoundedCornerShape = RoundedCornerShape(
    size = 16.dp
)


@Composable
private fun RecommendUserProfile(
    profile: String,
    name: String,
    takers: Int,
    createAt: String,
    onClickUserProfile: (() -> Unit)? = null,
    isFollowing: Boolean,
    onClickFollowing: (Boolean) -> Unit,
) {
    Row(
        modifier = Modifier.padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        QuackImage(
            src = profile,
            size = HomeProfileSize,
            shape = HomeProfileShape,
            onClick = {
                if (onClickUserProfile != null) {
                    onClickUserProfile()
                }
            },
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column {
            QuackSubtitle2(
                text = name,
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row {
                QuackBody3(
                    text = "응시자 $takers",
                    color = QuackColor.Gray2,
                )

                Spacer(modifier = Modifier.width(4.dp))

                QuackBody3(
                    text = "·",
                    color = QuackColor.Gray2,
                )

                Spacer(modifier = Modifier.width(4.dp))

                QuackBody3(
                    text = createAt,
                    color = QuackColor.Gray2,
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        QuackBody2(
            text = if (isFollowing) "팔로우" else "팔로잉",
            color = if (isFollowing) QuackColor.Gray1 else QuackColor.DuckieOrange,
            onClick = { onClickFollowing(!isFollowing) },
            rippleEnabled = false,
        )
    }
}

@Composable
private fun TestCoverWithMaker(
    cover: String,
    profile: String,
    title: String,
    name: String,
    takers: Int,
    createAt: String,
    onClickTestCover: () -> Unit,
    onClickUserProfile: () -> Unit,
) {
    Column {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)
                .quackClickable {
                    onClickTestCover()
                },
            model = cover,
            contentDescription = null,
        )

        Spacer(modifier = Modifier.height(12.dp))

        HomeTestMaker(
            profile = profile,
            title = title,
            name = name,
            takers = takers,
            createAt = createAt,
            onClickUserProfile = onClickUserProfile,
        )
    }
}
