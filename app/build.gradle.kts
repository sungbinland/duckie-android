/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:Suppress("DSL_SCOPE_VIOLATION")

plugins {
    id(ConventionEnum.AndroidApplication)
    id(libs.plugins.di.hilt.get().pluginId)
    `kotlin-kapt`
}

android {
    namespace = "team.duckie.app.android"
}

dependencies {
    implementations(
        libs.di.hilt.core,
        projects.presentation,
    )
    kapt(libs.di.hilt.compiler)
}
