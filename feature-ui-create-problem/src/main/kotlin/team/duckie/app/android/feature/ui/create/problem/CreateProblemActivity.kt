/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:OptIn(
    ExperimentalLifecycleComposeApi::class,
)

package team.duckie.app.android.feature.ui.create.problem

import android.os.Bundle
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import team.duckie.app.android.feature.ui.create.problem.screen.AdditionalInformationScreen
import team.duckie.app.android.feature.ui.create.problem.screen.CreateProblemScreen
import team.duckie.app.android.feature.ui.create.problem.screen.ExamInformationScreen
import team.duckie.app.android.feature.ui.create.problem.screen.FindExamAreaScreen
import team.duckie.app.android.feature.ui.create.problem.viewmodel.CreateProblemViewModel
import team.duckie.app.android.feature.ui.create.problem.viewmodel.sideeffect.CreateProblemSideEffect
import team.duckie.app.android.feature.ui.create.problem.viewmodel.state.CreateProblemStep
import team.duckie.app.android.util.compose.LocalViewModel
import team.duckie.app.android.util.ui.BaseActivity
import team.duckie.app.android.util.ui.finishWithAnimation
import team.duckie.quackquack.ui.animation.QuackAnimatedContent
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.theme.QuackTheme
import javax.inject.Inject

@AndroidEntryPoint
class CreateProblemActivity : BaseActivity() {

    @Inject
    internal lateinit var viewModel: CreateProblemViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val createProblemStep = viewModel.state.collectAsStateWithLifecycle().value.createProblemStep

            BackHandler {
                when (createProblemStep) {
                    CreateProblemStep.ExamInformation -> finishWithAnimation()
                    CreateProblemStep.FindExamArea -> viewModel.navigateStep(CreateProblemStep.ExamInformation)
                    else -> viewModel.navigateStep(createProblemStep.minus(1))
                }
            }

            LaunchedEffect(viewModel.sideEffect) {
                viewModel.sideEffect
                    .onEach(::handleSideEffect)
                    .launchIn(this)
            }

            QuackTheme {
                CompositionLocalProvider(
                    LocalViewModel provides viewModel,
                ) {
                    QuackAnimatedContent(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = QuackColor.White.composeColor),
                        targetState = createProblemStep,
                    ) { step: CreateProblemStep ->
                        when (step) {
                            CreateProblemStep.ExamInformation -> ExamInformationScreen()
                            CreateProblemStep.FindExamArea -> FindExamAreaScreen()
                            CreateProblemStep.CreateProblem -> CreateProblemScreen(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .statusBarsPadding()
                            )
                            CreateProblemStep.AdditionalInformation -> AdditionalInformationScreen(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .statusBarsPadding()
                            )
                        }
                    }
                }
            }
        }
    }

    private fun handleSideEffect(sideEffect: CreateProblemSideEffect) {
        when (sideEffect) {
            CreateProblemSideEffect.FinishActivity -> {
                finishWithAnimation()
            }
            is CreateProblemSideEffect.ReportError -> {
                Firebase.crashlytics.recordException(sideEffect.exception)
            }
            is CreateProblemSideEffect.UpdateGalleryImages -> {
                viewModel.addGalleryImages(sideEffect.images)
            }
        }
    }
}
