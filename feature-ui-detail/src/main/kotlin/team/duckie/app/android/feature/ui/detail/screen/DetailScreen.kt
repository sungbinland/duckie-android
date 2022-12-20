/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.detail.screen

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.persistentListOf
import team.duckie.app.android.feature.ui.detail.R
import team.duckie.app.android.feature.ui.detail.viewmodel.DetailViewModel
import team.duckie.app.android.feature.ui.detail.viewmodel.sideeffect.DetailSideEffect
import team.duckie.app.android.util.compose.CoroutineScopeContent
import team.duckie.app.android.util.compose.LocalViewModel
import team.duckie.app.android.util.compose.asLoose
import team.duckie.app.android.util.compose.rememberToast
import team.duckie.app.android.util.kotlin.fastFirstOrNull
import team.duckie.app.android.util.kotlin.npe
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.QuackBody2
import team.duckie.quackquack.ui.component.QuackBody3
import team.duckie.quackquack.ui.component.QuackDivider
import team.duckie.quackquack.ui.component.QuackHeadLine2
import team.duckie.quackquack.ui.component.QuackImage
import team.duckie.quackquack.ui.component.QuackSingeLazyRowTag
import team.duckie.quackquack.ui.component.QuackSmallButton
import team.duckie.quackquack.ui.component.QuackSmallButtonType
import team.duckie.quackquack.ui.component.QuackTagType
import team.duckie.quackquack.ui.component.QuackTopAppBar
import team.duckie.quackquack.ui.component.internal.QuackText
import team.duckie.quackquack.ui.icon.QuackIcon
import team.duckie.quackquack.ui.textstyle.QuackTextStyle

private const val DetailScreenTopAppBarLayoutId = "DetailScreenTopAppBar"
private const val DetailScreenContentLayoutId = "DetailScreenContent"
private const val DetailScreenBottomBarLayoutId = "DetailScreenBottomBar"

/** 상세 화면 Screen */
@Composable
internal fun DetailScreen(modifier: Modifier = Modifier) = CoroutineScopeContent {
    val activity = LocalContext.current as Activity
    val viewModel = LocalViewModel.current as DetailViewModel
    val toast = rememberToast()

    LaunchedEffect(Unit) {
        viewModel.sendToast("상세 화면 진입")
    }

    LaunchedEffect(viewModel.sideEffect) {
        viewModel.sideEffect.collect { effect ->
            when (effect) {
                is DetailSideEffect.SendToast -> toast(effect.message)
                is DetailSideEffect.Click -> Unit
            }
        }
    }

    Layout(
        modifier = modifier,
        content = {
            // 상단 탭바 Layout
            // TODO(riflockle7): trailingIcon 의 경우, 추후 커스텀 composable 을 넣을 수 있게 하면 어떨지...?
            QuackTopAppBar(
                modifier = Modifier.layoutId(DetailScreenTopAppBarLayoutId),
                leadingIcon = QuackIcon.ArrowBack,
                trailingIcon = QuackIcon.More,
                onLeadingIconClick = { activity.finish() },
                onTrailingIconClick = {},
            )
            // content Layout
            DetailContentLayout()
            // 최하단 Layout
            DetailBottomLayout(
                modifier = Modifier
                    .layoutId(DetailScreenBottomBarLayoutId)
                    .background(color = QuackColor.White.composeColor),
                onHeartClick = { },
                onChallengeClick = { },
            )
        }
    ) { measurableItems, constraints ->
        // 1. topAppBar, bottomBar 높이값 측정
        val looseConstraints = constraints.asLoose()

        val topAppBarMeasurable = measurableItems.fastFirstOrNull { measureItem ->
            measureItem.layoutId == DetailScreenTopAppBarLayoutId
        }?.measure(looseConstraints) ?: npe()
        val topAppBarHeight = topAppBarMeasurable.height

        val bottomBarMeasurable = measurableItems.fastFirstOrNull { measurable ->
            measurable.layoutId == DetailScreenBottomBarLayoutId
        }?.measure(looseConstraints) ?: npe()
        val bottomBarHeight = bottomBarMeasurable.height

        // 2. content 제약 설정 및 content 높이값 측정
        val contentHeight = constraints.maxHeight - topAppBarHeight - bottomBarHeight
        val contentConstraints = constraints.copy(
            minHeight = contentHeight,
            maxHeight = contentHeight,
        )
        val contentMeasurable = measurableItems.fastFirstOrNull { measurable ->
            measurable.layoutId == DetailScreenContentLayoutId
        }?.measure(contentConstraints) ?: npe()

        // 3. 위에서 추출한 값들을 활용해 레이아웃 위치 처리
        layout(
            width = constraints.maxWidth,
            height = constraints.maxHeight,
        ) {
            topAppBarMeasurable.place(
                x = 0,
                y = 0,
            )
            contentMeasurable.place(
                x = 0,
                y = topAppBarHeight,
            )
            bottomBarMeasurable.place(
                x = 0,
                y = topAppBarHeight + contentHeight,
            )
        }
    }
}

/** 상세 화면 컨텐츠 Layout */
@Composable
private fun DetailContentLayout() {
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp.dp
    val detailImageWidthDp = screenWidthDp - 32.dp

    Column(
        modifier = Modifier
            .layoutId(DetailScreenContentLayoutId)
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
    ) {
        // 그림
        QuackImage(
            size = DpSize(detailImageWidthDp, detailImageWidthDp * 240 / 328),
            padding = PaddingValues(
                top = 16.dp,
                start = 16.dp,
                end = 16.dp
            ),
            contentScale = ContentScale.FillWidth,
            src = R.drawable.img_duckie_detail_image_dummy,
        )
        // 공백
        Spacer(modifier = Modifier.height(12.dp))
        // 제목
        QuackHeadLine2(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = "제 1회 도로 패션영역"
        )
        // 공백
        Spacer(modifier = Modifier.height(8.dp))
        // 내용
        QuackBody2(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = "아 저 근데 너무 재밌을거 같아요 내 시험 최고"
        )
        // 공백
        Spacer(modifier = Modifier.height(12.dp))
        // 태그 목록
        QuackSingeLazyRowTag(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
            horizontalSpace = 4.dp,
            items = persistentListOf(
                "도로", "패션", "도로패션", "도로로", "Doro Driven Design"
            ),
            tagType = QuackTagType.Grayscale(""),
            onClick = {},
        )
        // 공백
        Spacer(modifier = Modifier.height(24.dp))
        // 구분선
        QuackDivider()
        // 프로필 Layout
        DetailProfileLayout()
        // 구분선
        QuackDivider()
        // 공백
        Spacer(modifier = Modifier.height(24.dp))
        // 점수 분포도 Layout
        DetailScoreDistributionLayout()
    }
}

/**
 * 상세 화면 프로필 Layout
 * TODO(riflockle7): 추후 공통화하기
 */
@Composable
private fun DetailProfileLayout() {
    Row(
        modifier = Modifier.padding(
            horizontal = 16.dp,
            vertical = 12.dp,
        ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 작성자 프로필 이미지
        QuackImage(
            src = team.duckie.quackquack.ui.R.drawable.quack_ic_profile_24,
            size = DpSize(32.dp, 32.dp)
        )
        // 공백
        Spacer(modifier = Modifier.width(8.dp))
        // 닉네임, 응시자, 일자 Layout
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                // 댓글 작성자 닉네임
                QuackBody3(
                    text = "닉네임",
                    onClick = {},
                    color = QuackColor.Black,
                )

                // 공백
                Spacer(modifier = Modifier.width(4.dp))
            }

            // 공백
            Spacer(modifier = Modifier.height(2.dp))

            // 응시자, 일자
            QuackBody3(
                text = stringResource(R.string.detail_num_date, "20", "1일 전"),
                color = QuackColor.Gray2
            )
        }
        // 공백
        Spacer(modifier = Modifier.weight(1f))
        // TODO(riflockle7): 추후 팔로우 완료 시에 대한 처리 필요
        // 팔로우 버튼
        QuackBody2(
            padding = PaddingValues(
                top = 8.dp,
                bottom = 8.dp,
            ),
            text = stringResource(R.string.detail_follow),
            color = QuackColor.DuckieOrange,
            onClick = { },
        )
    }
}

/** 상세 화면 점수 분포도 Layout */
@Composable
private fun DetailScoreDistributionLayout() {
    // 제목 Layout
    Row(
        modifier = Modifier.padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 제목
        QuackText(text = "점수 분포도", style = QuackTextStyle.Title2)
        // 공백
        Spacer(modifier = Modifier.weight(1f))
        // 정답률 텍스트
        QuackText(
            text = stringResource(R.string.detail_right_percent, "80%"),
            style = QuackTextStyle.Body2
        )
    }
    // 공백
    Spacer(modifier = Modifier.height(8.dp))
    // 분포도 레퍼
    QuackText(
        modifier = Modifier.padding(horizontal = 16.dp),
        text = "분포도 레퍼 필요",
        style = QuackTextStyle.Body2
    )
}

/** 상세 화면 최하단 Layout */
@Composable
private fun DetailBottomLayout(
    modifier: Modifier,
    onHeartClick: () -> Unit,
    onChallengeClick: () -> Unit,
) {
    Column(modifier = modifier) {
        // 구분선
        QuackDivider()
        // 버튼 모음 Layout
        // TODO(riflockle7): 추후 Layout 을 활용해 처리하기
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 9.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            // 좋아요 버튼
            QuackImage(
                src = team.duckie.quackquack.ui.R.drawable.quack_ic_heart_24,
                size = DpSize(24.dp, 24.dp),
                onClick = onHeartClick,
            )

            // 버튼
            QuackSmallButton(
                text = "하기싫음 하지마세요",
                type = QuackSmallButtonType.Fill,
                enabled = true,
                onClick = onChallengeClick,
            )
        }
    }
}
