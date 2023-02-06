/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:Suppress("unused")

package team.duckie.app.android.domain.exam.model

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList
import team.duckie.app.android.domain.category.model.Category
import team.duckie.app.android.domain.heart.model.Hearts
import team.duckie.app.android.domain.tag.model.Tag
import team.duckie.app.android.domain.user.model.User
import team.duckie.app.android.util.kotlin.OutOfDateApi

@OutOfDateApi
@Immutable
data class Exam(
    val id: Int,
    val title: String,
    val description: String,
    val thumbnailUrl: String?,
    val buttonTitle: String,
    val certifyingStatement: String,
    val solvedCount: Int,
    val answerRate: Float,
    val category: Category,
    val mainTag: Tag,
    val subTags: ImmutableList<Tag>,
    val problems: ImmutableList<Problem>,
    val type: String,
    val user: User,
    val status: String,
    val heart: Hearts?,
)
