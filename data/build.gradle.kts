/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:Suppress("DSL_SCOPE_VIOLATION", "UnstableApiUsage")

import DependencyHandler.Extensions.implementations

plugins {
    id(ConventionEnum.AndroidLibrary)
    id(ConventionEnum.JvmJUnit4)
    id(ConventionEnum.JvmDokka)
    id(libs.plugins.util.secrets.get().pluginId)
}

android {
    namespace = "team.duckie.app.android.data"

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementations(
        libs.login.kakao,
        libs.kotlin.coroutines,
        projects.domain,
        projects.utilKotlin,
    )
}
