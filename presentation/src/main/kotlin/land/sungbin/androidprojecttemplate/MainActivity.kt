package land.sungbin.androidprojecttemplate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import land.sungbin.androidprojecttemplate.navigation.MainNavigation
import land.sungbin.androidprojecttemplate.navigation.homeBottomNavItems
import team.duckie.quackquack.ui.component.QuackBottomNavigation
import team.duckie.quackquack.ui.theme.QuackTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    companion object {
        private const val InitialNavigationIndex = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuackTheme {
                var selectedNavigationIndex by remember {
                    mutableStateOf(InitialNavigationIndex)
                }
                val navController = rememberNavController()

                Column(modifier = Modifier.fillMaxSize()) {
                    Box(modifier = Modifier.weight(1f)) {
                        MainNavigation(
                            navController = navController,
                        )
                    }
                    QuackBottomNavigation(
                        selectedIndex = selectedNavigationIndex,
                        onClick = { index ->
                            selectedNavigationIndex = index
                            navController.navigate(homeBottomNavItems[index]) {
                                navController.graph.startDestinationRoute?.let { startDestinationRoute ->
                                    popUpTo(startDestinationRoute) { saveState = true }
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                    )
                }
            }
        }
    }
}
