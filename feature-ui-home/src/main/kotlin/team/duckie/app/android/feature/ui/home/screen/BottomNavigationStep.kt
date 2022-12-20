/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.home.screen

/**
 * BototmNavigation의 Step을 관리하는 enum class
 *
 * @param index step의 index
 *
 * @see HomeActivity
 */
enum class BottomNavigationStep(
    val index: Int,
) {
    HomeScreen(
        index = 0
    ),
    SearchScreen(
        index = 1
    ),
    RankingScreen(
        index = 2
    ),
    MyPageScreen(
        index = 3
    );

    companion object {
        fun toStep(value: Int) = BottomNavigationStep.values().first { it.index == value }
    }
}
