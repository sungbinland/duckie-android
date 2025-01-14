/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:Suppress(
    "UnstableApiUsage",
)

import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import team.duckie.app.android.convention.applyPlugins
import team.duckie.app.android.convention.configureApplication

/**
 * Android 프레임워크의 Library 환경을 구성합니다.
 */
internal class AndroidLibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            applyPlugins(
                "com.android.library",
                "kotlin-android"
            )

            extensions.configure<LibraryExtension> {
                configureApplication(this)
            }
        }
    }
}
