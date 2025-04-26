package com.firman.submission

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.firman.submission.ui.navigation.NavigationItem
import com.firman.submission.ui.navigation.Screen
import com.firman.submission.ui.screen.about.AboutScreen
import com.firman.submission.ui.screen.detail.DetailScreen
import com.firman.submission.ui.screen.home.HomeScreen
import com.firman.submission.ui.theme.SubmissionTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenshinCharacterApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val systemUiController = rememberSystemUiController()
    val primaryColor = MaterialTheme.colorScheme.primary

    SideEffect {
        systemUiController.setStatusBarColor(
            color = primaryColor,
            darkIcons = false
        )
    }

    Scaffold(
        bottomBar = {
            if (currentRoute != Screen.Detail.route) {
                BottomBar(navController)
            }
        },
        modifier = modifier
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    navigateToDetail = { characterId ->
                        navController.navigate(Screen.Detail.createRoute(characterId))
                    }
                )
            }
            composable(Screen.About.route) {
                AboutScreen()
            }
            composable(
                Screen.Detail.route,
                arguments = listOf(navArgument("characterId") { type = NavType.LongType })
            ) { backStackEntry ->
                val characterId = backStackEntry.arguments?.getLong("characterId") ?: 0L
                DetailScreen(
                    characterId = characterId.toString(),
                    onBackClick = {
                        navController.navigateUp()
                    }
                )
            }
        }
    }
}

@Composable
private fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavigationBar(modifier = modifier) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        val navigationItems = listOf(
            NavigationItem(
                title = stringResource(R.string.menu_home),
                icon = Icons.Default.Home,
                screen = Screen.Home
            ),
            NavigationItem(
                title = stringResource(R.string.menu_about),
                icon = Icons.Default.AccountCircle,
                screen = Screen.About
            ),
        )
        navigationItems.map { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title
                    )
                },
                label = { Text(item.title) },
                selected = currentRoute == item.screen.route,
                onClick = {
                    navController.navigate(item.screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GenshinCharacterAppPreview() {
    SubmissionTheme {
        GenshinCharacterApp()
    }
}
