/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.search.screen

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.orbitmvi.orbit.compose.collectAsState
import team.duckie.app.android.feature.ui.search.R
import team.duckie.app.android.feature.ui.search.constants.SearchStep
import team.duckie.app.android.feature.ui.search.viewmodel.SearchViewModel
import team.duckie.app.android.feature.ui.search.viewmodel.sideeffect.SearchSideEffect
import team.duckie.app.android.navigator.feature.detail.DetailNavigator
import team.duckie.app.android.shared.ui.compose.DuckieCircularProgressIndicator
import team.duckie.app.android.util.compose.systemBarPaddings
import team.duckie.app.android.util.kotlin.AllowMagicNumber
import team.duckie.app.android.util.ui.BaseActivity
import team.duckie.app.android.util.ui.const.Extras
import team.duckie.app.android.util.ui.finishWithAnimation
import team.duckie.app.android.util.ui.popStringExtra
import team.duckie.quackquack.ui.animation.QuackAnimatedContent
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.QuackBasicTextField
import team.duckie.quackquack.ui.component.QuackImage
import team.duckie.quackquack.ui.icon.QuackIcon
import team.duckie.quackquack.ui.theme.QuackTheme
import team.duckie.quackquack.ui.util.DpSize
import javax.inject.Inject

internal val SearchHorizontalPadding = PaddingValues(horizontal = 16.dp)

@AndroidEntryPoint
class SearchActivity : BaseActivity() {

    @Inject
    lateinit var detailNavigator: DetailNavigator

    private val vm: SearchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        intent.popStringExtra(Extras.SearchTag)?.let { str ->
            vm.updateSearchKeyword(
                keyword = str,
                debounce = false,
            )
        }

        setContent {
            val state = vm.collectAsState().value

            LaunchedEffect(key1 = vm) {
                vm.container.sideEffectFlow
                    .onEach(::handleSideEffect)
                    .launchIn(this)
            }

            QuackTheme {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    Column(
                        modifier = Modifier
                            .background(QuackColor.White.composeColor)
                            .padding(systemBarPaddings),
                    ) {
                        SearchTextFieldTopBar(
                            modifier = Modifier.padding(SearchHorizontalPadding),
                            searchKeyword = state.searchKeyword,
                            onSearchKeywordChanged = { keyword ->
                                vm.updateSearchKeyword(keyword = keyword)
                            },
                            onPrevious = {
                                finishWithAnimation()
                            },
                        )
                        QuackAnimatedContent(
                            targetState = state.searchStep,
                        ) { step ->
                            when (step) {
                                SearchStep.Search -> SearchScreen(vm = vm)
                                SearchStep.SearchResult -> SearchResultScreen(
                                    navigateDetail = { examId ->
                                        detailNavigator.navigateFrom(
                                            activity = this@SearchActivity,
                                            intentBuilder = {
                                                putExtra(Extras.ExamId, examId)
                                            },
                                        )
                                    },
                                )
                            }
                        }
                    }
                    if (state.isSearchLoading) {
                        DuckieCircularProgressIndicator()
                    }
                }
            }
        }
    }

    private fun handleSideEffect(sideEffect: SearchSideEffect) {
        when (sideEffect) {
            is SearchSideEffect.ReportError -> {
                Firebase.crashlytics.recordException(sideEffect.exception)
            }

            is SearchSideEffect.HideKeyBoard -> {
                val inputMethodManager =
                    getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(
                    currentFocus?.windowToken,
                    InputMethodManager.HIDE_NOT_ALWAYS,
                )
            }
        }
    }
}

@Composable
private fun SearchTextFieldTopBar(
    modifier: Modifier = Modifier,
    searchKeyword: String,
    onSearchKeywordChanged: (String) -> Unit,
    onPrevious: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        QuackImage(
            src = QuackIcon.ArrowBack,
            size = DpSize(all = 24.dp),
            onClick = onPrevious,
        )
        Spacer(modifier = Modifier.width(8.dp))
        // TODO(limsaehyun): QuackQuack를 통해서 underline이 없는 TextField로 교체해야 함
        @AllowMagicNumber(because = "임시로 구현한 컴포넌트")
        QuackBasicTextField(
            modifier = Modifier.offset(y = (-4).dp),
            text = searchKeyword,
            onTextChanged = { keyword ->
                onSearchKeywordChanged(keyword)
            },
            placeholderText = stringResource(id = R.string.try_search),
        )
    }
}
