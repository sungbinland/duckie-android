/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.solve.problem.examresult

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import team.duckie.app.android.feature.ui.solve.problem.examresult.screen.ExamResultScreen
import team.duckie.app.android.feature.ui.solve.problem.examresult.viewmodel.ExamResultViewModel
import team.duckie.app.android.util.ui.BaseActivity
import team.duckie.quackquack.ui.theme.QuackTheme

@AndroidEntryPoint
class ExamResultActivity : BaseActivity() {
    val viewModel: ExamResultViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuackTheme {
                ExamResultScreen()
            }
        }
    }
}