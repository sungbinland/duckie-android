/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.terms.mapper

import team.duckie.app.android.data._util.toDate
import team.duckie.app.android.data.terms.model.TermsResponseData
import team.duckie.app.android.domain.terms.model.Terms
import team.duckie.app.android.util.kotlin.exception.duckieResponseFieldNpe

internal fun TermsResponseData.toDomain() = Terms(
    id = id ?: duckieResponseFieldNpe("id"),
    version = version ?: duckieResponseFieldNpe("version"),
    condition = condition ?: duckieResponseFieldNpe("condition"),
    createdAt = createdAt?.toDate() ?: duckieResponseFieldNpe("createdAt"),
)
