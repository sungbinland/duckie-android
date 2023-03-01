/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.examInstance.repository

import androidx.compose.runtime.Immutable
import team.duckie.app.android.domain.exam.model.ExamInstanceBody
import team.duckie.app.android.domain.exam.model.ExamInstanceSubmit
import team.duckie.app.android.domain.exam.model.ExamInstanceSubmitBody
import team.duckie.app.android.domain.examInstance.model.ExamInstance

@Immutable
interface ExamInstanceRepository {
    suspend fun getExamInstance(examInstanceId: Int): ExamInstance

    suspend fun postExamInstance(examInstanceBody: ExamInstanceBody): ExamInstance

    suspend fun postExamInstanceSubmit(
        id: Int,
        examInstanceSubmitBody: ExamInstanceSubmitBody,
    ): ExamInstanceSubmit
}
