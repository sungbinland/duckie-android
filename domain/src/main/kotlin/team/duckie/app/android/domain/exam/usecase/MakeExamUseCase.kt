/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.exam.usecase

import team.duckie.app.android.domain.exam.model.ExamParam
import team.duckie.app.android.domain.exam.repository.ExamRepository

class MakeExamUseCase(
    private val examRepository: ExamRepository,
) {
    suspend operator fun invoke(examParam: ExamParam) = runCatching {
        examRepository.makeExam(examParam)
    }
}
