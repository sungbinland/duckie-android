/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.ranking.usecase

import team.duckie.app.android.domain.ranking.repository.RankingRepository
import javax.inject.Inject

class GetExamRankingsBySolvedCount @Inject constructor(
    private val repository: RankingRepository,
) {
    operator fun invoke(tagId: Int?) = repository.getExamRankingsBySolvedCount(tagId)
}