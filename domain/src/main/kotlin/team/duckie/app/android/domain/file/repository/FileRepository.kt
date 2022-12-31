/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.file.repository

import androidx.compose.runtime.Immutable
import java.io.File

@Immutable
interface FileRepository {
    suspend fun upload(file: File, type: String): String
}
