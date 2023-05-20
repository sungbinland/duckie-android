import DependencyHandler.Extensions.implementations

/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

plugins {
    id(ConventionEnum.AndroidLibrary)
}

android {
    namespace = "team.duckie.app.android.common.android"
}

dependencies {
    implementations(
        projects.common.kotlin,
        libs.androidx.lifecycle.savedstate,
        libs.ktx.lifecycle.runtime,
        libs.ktx.lifecycle.viewmodel,
        libs.compose.ui.activity,
    )
}
