/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.exam.model

import com.fasterxml.jackson.annotation.JsonProperty
import team.duckie.app.android.data.user.model.UserResponse

data class QuizInfoResponse(
    @JsonProperty("id")
    val id: Int? = null,
    @JsonProperty("correctProblemCount")
    val correctProblemCount: Int? = null,
    @JsonProperty("score")
    val score: Int? = null,
    @JsonProperty("time")
    val time: Int? = null,
    @JsonProperty("user")
    val user: UserResponse? = null,
)
