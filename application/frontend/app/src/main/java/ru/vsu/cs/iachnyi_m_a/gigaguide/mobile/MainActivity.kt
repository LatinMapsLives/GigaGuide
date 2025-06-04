package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.datastore.DataStoreManager
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.navigation.ExploreSightScreenClass
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.navigation.ExploreTourScreenClass
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.navigation.FavoriteScreenObject
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.navigation.HomeScreenObject
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.navigation.LoginScreenObject
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.navigation.MapScreenObject
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.navigation.NavBarItem
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.navigation.ProfileScreenObject
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.navigation.RegisterScreenObject
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.navigation.SearchScreenObject
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.navigation.SettingsScreenObject
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.navigation.SightPageScreenClass
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.navigation.SightReviewScreenClass
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.navigation.TourPageScreenClass
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.navigation.TourReviewScreenClass
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.ui.theme.GigaGuideMobileTheme
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.ui.theme.InfoContainer
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.ui.theme.MediumBlue
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.ui.theme.MediumGrey
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.ui.theme.SuccessContainer
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.ui.theme.White
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.util.GeoLocationProvider
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.util.LocaleManager
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.util.Pancake
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.util.RememberLocale
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.view.ExploreSightScreen
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.view.ExploreTourScreen
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.view.FavoritesScreen
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.view.HomeScreen
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.view.LoginScreen
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.view.MapScreen
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.view.ProfileScreen
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.view.RegisterScreen
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.view.ReviewScreen
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.view.SearchScreen
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.view.SettingsScreen
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.view.SightPageScreen
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.view.TourPageScreen
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.view.util.dropShadow
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.viewmodel.ExploreSightScreenViewModel
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.viewmodel.HomeScreenViewModel
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.viewmodel.MapScreenViewModel
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.viewmodel.SettingsScreenViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val startScreenObject = HomeScreenObject;

        var selectedNavItemIndex = mutableIntStateOf(0);
        var infoMessage = mutableStateOf("")
        var infoColor = mutableStateOf<Color>(Color(0))

        var geoLocationProvider = GeoLocationProvider(this)
        var dataStoreManager = DataStoreManager(this)

        var navItems = listOf(
            NavBarItem(R.drawable.home, HomeScreenObject, R.string.nav_label_home), NavBarItem(
                R.drawable.map, MapScreenObject, R.string.nav_label_map
            ), NavBarItem(
                R.drawable.bookmark, FavoriteScreenObject, R.string.nav_label_favorite
            ), NavBarItem(
                R.drawable.person_navbar, SettingsScreenObject, R.string.nav_label_settings
            )
        )

        geoLocationProvider.checkPermissions()

        setContent {

            RememberLocale(
                LocaleManager.currentLanguage,
                { LocaleManager.recomposeFlag = !LocaleManager.recomposeFlag })

            GigaGuideMobileTheme {


                var showNavigationBarMutableState by remember { mutableStateOf(false) };
                var infoVisibleMutableState by remember { mutableStateOf(false) }
                var errorContainerColor = Color(0xffff6666)
                var successContainerColor = SuccessContainer
                var infoContainerColor = InfoContainer
                var noInternetMessage = stringResource(R.string.error_no_internet)
                var serverUnavailableMessage = stringResource(R.string.error_server_unavailable)
                var serverErrorMessage = stringResource(R.string.error_server_error)

                LaunchedEffect(Unit) {
                    LocaleManager.currentLanguage = dataStoreManager.getCurrentLanguage()
                    Pancake.setMessageHandler(
                        onError = {
                            infoMessage.value = it
                            infoColor.value = errorContainerColor
                            infoVisibleMutableState = true
                            Log.e("ERR", "ERROR")
                        },
                        onInfo = {

                            infoMessage.value = it
                            infoColor.value = infoContainerColor
                            infoVisibleMutableState = true
                        },
                        onSuccess = {

                            infoMessage.value = it
                            infoColor.value = successContainerColor
                            infoVisibleMutableState = true

                        },
                        noInternetMessage = noInternetMessage,
                        serverUnavailableMessage = serverUnavailableMessage,
                        serverErrorMessage = serverErrorMessage
                    )
                }

                val homeScreenViewModel: HomeScreenViewModel = hiltViewModel<HomeScreenViewModel>()
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
                        composable<HomeScreenObject>(
                            enterTransition = {
                                if (Regex("(MapScreen|FavoriteScreen|SettingsScreen)").containsMatchIn(
                                        initialState.destination.route!!
                                    )
                                ) {
                                    slideInHorizontally(initialOffsetX = { w -> -w })
                                } else if (Regex("(SearchScreen)").containsMatchIn(initialState.destination.route!!)) {
                                    fadeIn()
                                } else {
                                    null
                                }
                            },
                            exitTransition = {
                                if (Regex("(MapScreen|FavoriteScreen|SettingsScreen)").containsMatchIn(
                                        targetState.destination.route!!
                                    )
                                ) {
                                    slideOutHorizontally(targetOffsetX = { w -> -w })
                                } else if (Regex("(SearchScreen)").containsMatchIn(targetState.destination.route!!)) {
                                    fadeOut()
                                } else {
                                    null
                                }
                            }
                        ) {
                            HomeScreen(
                                homeScreenViewModel = homeScreenViewModel,
                                navController = navController
                            );
                            showNavigationBarMutableState = true;
                        }
                        composable<MapScreenObject>(
                            enterTransition = {
                                if (Regex("(HomeScreen)").containsMatchIn(initialState.destination.route!!)) {
                                    slideInHorizontally(initialOffsetX = { w -> w })
                                } else if (Regex("(FavoriteScreen|SettingsScreen)").containsMatchIn(
                                        initialState.destination.route!!
                                    )
                                ) {
                                    slideInHorizontally(initialOffsetX = { w -> -w })
                                } else {
                                    null
                                }
                            },
                            exitTransition = {
                                if (Regex("(HomeScreen)").containsMatchIn(targetState.destination.route!!)) {
                                    slideOutHorizontally(targetOffsetX = { w -> w })
                                } else if (Regex("(FavoriteScreen|SettingsScreen)").containsMatchIn(
                                        targetState.destination.route!!
                                    )
                                ) {
                                    slideOutHorizontally(targetOffsetX = { w -> -w })
                                } else {
                                    null
                                }
                            }) {
                            MapScreen(
                                navController = navController,
                                mapScreenViewModel = mapScreenViewModel,
                                locationProvider = geoLocationProvider
                            )
                            showNavigationBarMutableState = true;
                        }
                        composable<FavoriteScreenObject>(
                            enterTransition = {
                                if (Regex("(MapScreen|HomeScreen)").containsMatchIn(initialState.destination.route!!)) {
                                    slideInHorizontally(initialOffsetX = { w -> w })
                                } else if (Regex("(SettingsScreen)").containsMatchIn(initialState.destination.route!!)) {
                                    slideInHorizontally(initialOffsetX = { w -> -w })
                                } else {
                                    null
                                }
                            },
                            exitTransition = {
                                if (Regex("(MapScreen|HomeScreen)").containsMatchIn(targetState.destination.route!!)) {
                                    slideOutHorizontally(targetOffsetX = { w -> w })
                                } else if (Regex("(SettingsScreen)").containsMatchIn(targetState.destination.route!!)) {
                                    slideOutHorizontally(targetOffsetX = { w -> -w })
                                } else {
                                    null
                                }
                            }) {
                            FavoritesScreen(
                                navController = navController
                            )
                            showNavigationBarMutableState = true;
                        }
                        composable<SettingsScreenObject>(
                            enterTransition = {
                                if (Regex("(FavoriteScreen|MapScreen|HomeScreen)").containsMatchIn(
                                        initialState.destination.route!!
                                    )
                                ) {
                                    slideInHorizontally(initialOffsetX = { w -> w })
                                } else {
                                    null
                                }
                            },
                            exitTransition = {
                                if (Regex("(FavoriteScreen|MapScreen|HomeScreen)").containsMatchIn(
                                        targetState.destination.route!!
                                    )
                                ) {
                                    slideOutHorizontally(targetOffsetX = { w -> w })
                                } else {
                                    null
                                }
                            }
                        ) {
                            showNavigationBarMutableState = true;
                            SettingsScreen(
                                navController = navController,
                                settingsScreenViewModel = hiltViewModel<SettingsScreenViewModel>()
                            )
                        }
                        composable<LoginScreenObject>(
                            enterTransition = {
                                if (!Regex("(RegisterScreen)").containsMatchIn(
                                        initialState.destination.route!!
                                    )
                                ) {
                                    slideInVertically(initialOffsetY = { h -> h })
                                } else {
                                    null
                                }
                            },
                            exitTransition = {
                                if (!Regex("(RegisterScreen)").containsMatchIn(
                                        targetState.destination.route!!
                                    )
                                ) {
                                    slideOutVertically(targetOffsetY = { h -> h })
                                } else {
                                    null
                                }
                            }) {

                            showNavigationBarMutableState = false
                            LoginScreen(
                                navController = navController
                            )
                        }
                        composable<RegisterScreenObject>(enterTransition = {
                            slideInVertically(
                                initialOffsetY = { h -> h })
                        }, exitTransition = { slideOutVertically(targetOffsetY = { h -> h }) }) {
                            showNavigationBarMutableState = false
                            RegisterScreen(
                                navController = navController
                            )
                        }
                        composable<SightPageScreenClass>(
                            enterTransition = {
                                if (Regex("(HomeScreen|FavoriteScreen|SearchScreen|MapScreen|TourPageScreen)").containsMatchIn(
                                        initialState.destination.route!!
                                    )
                                ) {
                                    slideInHorizontally(initialOffsetX = { w -> w })
                                } else {
                                    null
                                }
                            },
                            exitTransition = {
                                if (Regex("(HomeScreen|FavoriteScreen|SearchScreen|MapScreen|TourPageScreen)").containsMatchIn(
                                        targetState.destination.route!!
                                    )
                                ) {
                                    slideOutHorizontally(targetOffsetX = { w -> w })
                                } else {
                                    null
                                }
                            }
                        ) {
                            val args = it.toRoute<SightPageScreenClass>()
                            showNavigationBarMutableState = false
                            SightPageScreen(
                                sightId = args.sightId,
                                navController = navController,
                            )
                        }
                        composable<ExploreSightScreenClass>(
                            enterTransition = {
                                slideInHorizontally(initialOffsetX = { w -> w })
                            },
                            exitTransition = {
                                slideOutHorizontally(targetOffsetX = { w -> w })
                            }) {
                            val args = it.toRoute<ExploreSightScreenClass>()
                            showNavigationBarMutableState = false;
                            ExploreSightScreen(
                                exploreSightScreenViewModel = hiltViewModel<ExploreSightScreenViewModel>(),
                                navController = navController,
                                sightId = args.sightId,
                                context = this@MainActivity,
                                locationProvider = geoLocationProvider
                            )
                        }
                        composable<SightReviewScreenClass>(
                            enterTransition = {
                                if (Regex("(SightPageScreen)").containsMatchIn(
                                        initialState.destination.route!!
                                    )
                                ) {
                                    slideInHorizontally(initialOffsetX = { h -> h })
                                } else {
                                    null
                                }
                            },
                            exitTransition = {
                                if (Regex("(SightPageScreen)").containsMatchIn(
                                        targetState.destination.route!!
                                    )
                                ) {
                                    slideOutHorizontally(targetOffsetX = { h -> h })
                                } else {
                                    null
                                }
                            }) {
                            val args = it.toRoute<SightReviewScreenClass>()
                            showNavigationBarMutableState = false
                            ReviewScreen(
                                objectId = args.sightId,
                                navController = navController,
                                isTour = false
                            )
                        }
                        composable<TourPageScreenClass>(
                            enterTransition = {
                                if (Regex("(HomeScreen|FavoriteScreen|SearchScreen)").containsMatchIn(
                                        initialState.destination.route!!
                                    )
                                ) {
                                    slideInHorizontally(initialOffsetX = { w -> w })
                                } else {
                                    null
                                }
                            },
                            exitTransition = {
                                if (Regex("(HomeScreen|FavoriteScreen|SearchScreen)").containsMatchIn(
                                        targetState.destination.route!!
                                    )
                                ) {
                                    slideOutHorizontally(targetOffsetX = { w -> w })
                                } else {
                                    null
                                }
                            }) {
                            val args = it.toRoute<TourPageScreenClass>()
                            showNavigationBarMutableState = false
                            TourPageScreen(tourId = args.tourId, navController = navController)
                        }
                        composable<TourReviewScreenClass>(
                            enterTransition = {
                                if (Regex("(TourPageScreen)").containsMatchIn(
                                        initialState.destination.route!!
                                    )
                                ) {
                                    slideInHorizontally(initialOffsetX = { h -> h })
                                } else {
                                    null
                                }
                            },
                            exitTransition = {
                                if (Regex("(TourPageScreen)").containsMatchIn(
                                        targetState.destination.route!!
                                    )
                                ) {
                                    slideOutHorizontally(targetOffsetX = { h -> h })
                                } else {
                                    null
                                }
                            }) {
                            val args = it.toRoute<TourReviewScreenClass>()
                            showNavigationBarMutableState = false
                            ReviewScreen(
                                objectId = args.tourId,
                                navController = navController,
                                isTour = true
                            )
                        }
                        composable<SearchScreenObject>(
                            enterTransition = { fadeIn() },
                            exitTransition = { fadeOut() }
                        ) {
                            showNavigationBarMutableState = false
                            SearchScreen(navController = navController)
                        }
                        composable<ExploreTourScreenClass> (enterTransition = {slideInHorizontally(initialOffsetX = {h->h})},
                            exitTransition = {slideOutHorizontally(targetOffsetX = {h->h})}) {
                            val args = it.toRoute<ExploreTourScreenClass>()
                            showNavigationBarMutableState = false
                            ExploreTourScreen(
                                context = this@MainActivity,
                                tourId = args.tourId,
                                navController = navController,
                                locationProvider = geoLocationProvider
                            )
                        }
                        composable<ProfileScreenObject>(enterTransition = {
                            slideInHorizontally(
                                initialOffsetX = { w -> w })
                        }, exitTransition = { slideOutHorizontally(targetOffsetX = { w -> w }) }) {
                            showNavigationBarMutableState = false
                            ProfileScreen(navController = navController)
                        }
                    }
                    AnimatedVisibility(
                        modifier = Modifier.align(Alignment.BottomCenter),
                        visible = showNavigationBarMutableState,
                        enter = slideInVertically(initialOffsetY = { h -> h + 60 }),
                        exit = slideOutVertically(targetOffsetY = { h -> h + 60 })
                    ) {
                        //key (LocaleManager.recomposeFlag){
                        //Log.e("LANG", "KEY " + LocaleManager.currentLanguage)
                        BottomNavigationBar(
                            modifier = Modifier,
                            navItems = navItems,
                            selectedIndex = selectedNavItemIndex,
                            navController = navController
                        )
                        //}
                    }
                    AnimatedVisibility(
                        modifier = Modifier.align(Alignment.TopCenter),
                        visible = infoVisibleMutableState,
                        enter = slideInVertically(initialOffsetY = { h -> -h }),
                        exit = slideOutVertically(targetOffsetY = { h -> -h })
                    ) {
                        Row(
                            modifier = Modifier
                                .clickable(onClick = {}, enabled = false)
                                .fillMaxWidth()
                                .padding(10.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(infoColor.value)
                                .padding(15.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                modifier = Modifier.weight(1f),
                                text = infoMessage.value,
                                style = MaterialTheme.typography.titleMedium,
                                color = White
                            )
                            Icon(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .clickable(onClick = { infoVisibleMutableState = false })
                                    .background(White)
                                    .padding(5.dp)
                                    .size(30.dp),
                                imageVector = Icons.Filled.Close,
                                contentDescription = null,
                                tint = MediumGrey,
                            )
                        }
                    }

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
        key(LocaleManager.recomposeFlag) {
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
                        Text(text = stringResource(navItem.iconLabel));
                    },
                    alwaysShowLabel = false,
                )

            }

        }

    }

}