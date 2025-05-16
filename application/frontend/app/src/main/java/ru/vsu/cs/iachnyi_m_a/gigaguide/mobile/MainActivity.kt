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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import dagger.hilt.android.AndroidEntryPoint
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.navigation.ExploreSightScreenClass
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.navigation.FavoriteScreenObject
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.navigation.HomeScreenObject
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.navigation.LoginScreenObject
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.navigation.MapScreenObject
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.navigation.NavBarItem
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.navigation.RegisterScreenObject
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.navigation.ReviewScreenClass
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.navigation.SearchScreenObject
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.navigation.SettingsScreenObject
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.navigation.SightPageScreenClass
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.navigation.TourPageScreenClass
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.ui.theme.GigaGuideMobileTheme
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.ui.theme.MediumBlue
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.ui.theme.MediumGrey
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.view.ExploreSightScreen
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.view.FavoritesScreen
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.view.HomeScreen
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.view.LoginScreen
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.view.MapScreen
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.view.RegisterScreen
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.view.ReviewScreen
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.view.SearchScreen
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.view.SettingsScreen
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.view.SightPageScreen
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.view.TourPageScreen
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.view.util.dropShadow
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.viewmodel.ExploreSightScreenViewModel
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.viewmodel.FavoritesScreenViewModel
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.viewmodel.HomeScreenViewModel
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.viewmodel.MapScreenViewModel
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.viewmodel.SettingsScreenViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val startScreenObject = HomeScreenObject;

        var navItems = listOf(
            NavBarItem(R.drawable.home, HomeScreenObject, R.string.nav_label_home), NavBarItem(
                R.drawable.map, MapScreenObject, R.string.nav_label_map
            ), NavBarItem(
                R.drawable.bookmark, FavoriteScreenObject, R.string.nav_label_favorite
            ), NavBarItem(
                R.drawable.person_navbar, SettingsScreenObject, R.string.nav_label_settings
            )
        )


        var selectedNavItemIndex = mutableIntStateOf(0);
        var showNavigationBar = mutableStateOf(false);

        setContent {
            GigaGuideMobileTheme {
                val homeScreenViewModel: HomeScreenViewModel = hiltViewModel<HomeScreenViewModel>()
                val favoritesScreenViewModel: FavoritesScreenViewModel = hiltViewModel<FavoritesScreenViewModel>()
                val mapScreenViewModel: MapScreenViewModel = hiltViewModel<MapScreenViewModel>()

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
                            HomeScreen(
                                homeScreenViewModel = homeScreenViewModel,
                                navController = navController
                            );
                            showNavigationBar.value = true;
                        }
                        composable<MapScreenObject> {
                            MapScreen(
                                navController = navController,
                                mapScreenViewModel = mapScreenViewModel
                            )
                            showNavigationBar.value = true;
                        }
                        composable<FavoriteScreenObject> {
                            FavoritesScreen(
                                navController = navController
                            )
                            showNavigationBar.value = true;
                        }
                        composable<SettingsScreenObject> {
                            showNavigationBar.value = true;
                            SettingsScreen(navController = navController, settingsScreenViewModel = hiltViewModel<SettingsScreenViewModel>())
                        }
                        composable<LoginScreenObject> {
                            showNavigationBar.value = false
                            LoginScreen(
                                navController = navController
                            )
                        }
                        composable<RegisterScreenObject> {
                            showNavigationBar.value = false
                            RegisterScreen(
                                navController = navController
                            )
                        }
                        composable<SightPageScreenClass> {
                            val args = it.toRoute<SightPageScreenClass>()
                            showNavigationBar.value = false
                            SightPageScreen(
                                sightId = args.sightId,
                                navController = navController,
                            )
                        }
                        composable<ExploreSightScreenClass>{
                            val args = it.toRoute<ExploreSightScreenClass>()
                            showNavigationBar.value = false;
                            ExploreSightScreen(exploreSightScreenViewModel = hiltViewModel<ExploreSightScreenViewModel>(), navController = navController, sightId = args.sightId, context = this@MainActivity)
                        }
                        composable<ReviewScreenClass>{
                            val args = it.toRoute<ReviewScreenClass>()
                            showNavigationBar.value = false
                            ReviewScreen(sightId = args.sightId, navController = navController)
                        }
                        composable<SearchScreenObject>{
                            showNavigationBar.value = false
                            SearchScreen(navController = navController)
                        }
                        composable<TourPageScreenClass>{
                            val args = it.toRoute<TourPageScreenClass>()
                            showNavigationBar.value = false
                            TourPageScreen(tourId = args.tourId, navController = navController)
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