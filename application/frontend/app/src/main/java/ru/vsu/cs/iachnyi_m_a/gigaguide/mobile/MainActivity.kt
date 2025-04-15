package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.navigation.FavoriteScreenObject
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.navigation.HomeScreenObject
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.navigation.LoginScreenObject
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.navigation.NavBarItem
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.navigation.RegisterScreenObject
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.navigation.SettingsScreenObject
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.ui.theme.GigaGuideMobileTheme
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.ui.theme.MediumBlue
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.ui.theme.MediumGrey
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.view.FavoriteScreen
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.view.HomeScreen
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.view.LoginScreen
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.view.RegisterScreen
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.view.SettingsScreen
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.view.util.dropShadow
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.viewmodel.FavoriteScreenViewModel
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.viewmodel.HomeScreenViewModel
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.viewmodel.LoginScreenViewModel
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.viewmodel.RegisterScreenViewModel


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val homeScreenViewModel: HomeScreenViewModel =
            ViewModelProvider(this)[HomeScreenViewModel::class]
        val loginScreenViewModel = ViewModelProvider(this)[LoginScreenViewModel::class]
        val registerScreenViewModel = ViewModelProvider(this)[RegisterScreenViewModel::class]
        val favoriteScreenViewModel = ViewModelProvider(this)[FavoriteScreenViewModel::class]

        val startScreenObject = HomeScreenObject;

        var navItems = listOf(
            NavBarItem(R.drawable.home, HomeScreenObject, R.string.nav_label_home), NavBarItem(
                R.drawable.map, HomeScreenObject, R.string.nav_label_map
            ), NavBarItem(
                R.drawable.bookmark, FavoriteScreenObject, R.string.nav_label_favorite
            ), NavBarItem(
                R.drawable.person, SettingsScreenObject, R.string.nav_label_settings
            )
        )


        var selectedNavItemIndex = mutableIntStateOf(0);
        var showNavigationBar = mutableStateOf(false);


        setContent {
            GigaGuideMobileTheme {

                val navController = rememberNavController();

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = MaterialTheme.colorScheme.background),
                    contentAlignment = Alignment.TopCenter
                ) {
                    NavHost(
                        navController = navController, startDestination = startScreenObject,
                        enterTransition = { EnterTransition.None },
                        exitTransition = { ExitTransition.None },
                        popEnterTransition = { EnterTransition.None },
                        popExitTransition = { ExitTransition.None },
                    ) {
                        composable<HomeScreenObject> {
                            HomeScreen(homeScreenViewModel = homeScreenViewModel);
                            showNavigationBar.value = true;
                        }
                        composable<FavoriteScreenObject> {
                            FavoriteScreen(
                                favoriteScreenViewModel = favoriteScreenViewModel,
                                navController = navController
                            )
                            showNavigationBar.value = true;
                        }
                        composable<SettingsScreenObject> {
                            showNavigationBar.value = true;
                            SettingsScreen(navController = navController)
                        }
                        composable<LoginScreenObject> {
                            showNavigationBar.value = false
                            LoginScreen(
                                navController = navController,
                                loginScreenViewModel = loginScreenViewModel
                            )
                        }
                        composable<RegisterScreenObject> {
                            showNavigationBar.value = false
                            RegisterScreen(
                                navController = navController,
                                registerScreenViewModel = registerScreenViewModel
                            )
                        }
                    }
                    if (showNavigationBar.value) BottomNavigationBar(
                        modifier = Modifier.align(Alignment.BottomCenter),
                        navItems = navItems,
                        selectedIndex = selectedNavItemIndex,
                        navController = navController
                    )
                }
            }
        }
    }
}


@Composable
fun BottomNavigationBar(
    modifier: Modifier,
    navController: NavController,
    navItems: List<NavBarItem>,
    selectedIndex: MutableState<Int>
) {

    NavigationBar(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
            .dropShadow(offsetX = 0.dp, offsetY = 0.dp, blur = 16.dp),
        containerColor = MaterialTheme.colorScheme.background
    ) {
        navItems.forEachIndexed { i, navItem ->
            NavigationBarItem(
                onClick = {
                    if (selectedIndex.value != i) {
                        selectedIndex.value = i
                        navController.navigate(navItem.screenObject)
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MediumBlue,
                    unselectedIconColor = MediumGrey,
                    selectedTextColor = MaterialTheme.colorScheme.onBackground,
                    indicatorColor = Color(0x00000000)
                ),
                icon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(navItem.iconId),
                        contentDescription = "das",
                    )
                },
                selected = selectedIndex.value == i,
                label = {
                    Text(text = stringResource(navItem.iconLabelId));
                },
                alwaysShowLabel = false,
            )

        }
    }
}