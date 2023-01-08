/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.recommendation.model

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList

@Immutable
data class RecommendationFeeds(
    val jumbotrons: ImmutableList<RecommendationJumbotronItem>,
    val recommendations: ImmutableList<RecommendationItem>,
    val page: Int,
    val offset: Int,
    val limit: Int,
)
