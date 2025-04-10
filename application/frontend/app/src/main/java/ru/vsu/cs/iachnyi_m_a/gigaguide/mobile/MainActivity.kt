package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.ui.theme.GigaGuideMobileTheme
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.ui.theme.MediumBlue
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.ui.theme.MediumGrey
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.view.HomeScreen
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.view.LoginScreen
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.view.SettingsScreen
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.view.util.dropShadow
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.viewmodel.HomeViewModel
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.viewmodel.LoginViewModel
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.viewmodel.NavigationBarViewModel
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.viewmodel.NavigationViewModel
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.viewmodel.ScreenName


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val homeViewModel: HomeViewModel = ViewModelProvider(this)[HomeViewModel::class]
        val navigationBarViewModel = ViewModelProvider(this)[NavigationBarViewModel::class]
        val navigationViewModel = ViewModelProvider(this)[NavigationViewModel::class]
        val loginViewModel = ViewModelProvider(this)[LoginViewModel::class]


        setContent {
            GigaGuideMobileTheme {
                Column(
                    modifier = Modifier
                        .fillMaxSize().background(color = MaterialTheme.colorScheme.background),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    when(navigationViewModel.currentScreen.value){
                        ScreenName.HOME -> HomeScreen(homeViewModel)
                        ScreenName.MAP -> HomeScreen(homeViewModel)
                        ScreenName.FAVORITE -> HomeScreen(homeViewModel)
                        ScreenName.SETTINGS -> SettingsScreen(navigationViewModel)
                        ScreenName.LOGIN -> LoginScreen(loginViewModel = loginViewModel, navigationViewModel = navigationViewModel)
                    }
                    if(navigationViewModel.showNavigationBar){
                        BottomNavigationBar(navigationBarViewModel, navigationViewModel)
                    }
                }
            }
        }
    }

}


@Composable
fun BottomNavigationBar(
    navigationBarViewModel: NavigationBarViewModel,
    navigationViewModel: NavigationViewModel
) {

    NavigationBar(modifier = Modifier.background(MaterialTheme.colorScheme.background).dropShadow(offsetX = 0.dp, offsetY = 0.dp, blur = 16.dp), containerColor = MaterialTheme.colorScheme.background) {
        navigationBarViewModel.iconIds.forEachIndexed { i, iconId ->
            NavigationBarItem(
                onClick = {
                    navigationViewModel.currentScreen.value = navigationBarViewModel.screenLinks[i]
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MediumBlue,
                    unselectedIconColor = MediumGrey,
                    selectedTextColor = MaterialTheme.colorScheme.onBackground,
                    indicatorColor = Color(0x00000000)
                ),
                icon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(iconId),
                        contentDescription = "das",
                    )
                },
                selected = navigationBarViewModel.screenLinks[i] == navigationViewModel.currentScreen.value,
                label = {
                    Text(text = stringResource(navigationBarViewModel.iconLabels[i]));
                },
                alwaysShowLabel = false,
            )

        }
    }
}