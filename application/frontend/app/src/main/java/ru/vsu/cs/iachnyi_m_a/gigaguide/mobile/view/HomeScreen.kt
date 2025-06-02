package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.R
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.navigation.HomeScreenObject
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.navigation.SearchScreenObject
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.navigation.SightPageScreenClass
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.ui.theme.GigaGuideMobileTheme
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.ui.theme.MediumBlue
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.util.CurrentThemeSettings
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.util.LocaleManager
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.util.RememberLocale
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.view.util.dropShadow
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.viewmodel.HomeScreenViewModel

@Composable
fun HomeScreen(
    homeScreenViewModel: HomeScreenViewModel = hiltViewModel<HomeScreenViewModel>(),
    navController: NavController
) {

    Column(
        Modifier
            .fillMaxWidth()
    ) {
        LaunchedEffect(LocaleManager.recomposeFlag) {
            homeScreenViewModel.loadClosestTours()
            homeScreenViewModel.updateAppTheme()
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .height(94.dp)
                .padding(20.dp)
        ) {
            Image(
                imageVector = if (!CurrentThemeSettings.isAppInDarkTheme()) ImageVector.vectorResource(id = R.drawable.logo) else ImageVector.vectorResource(
                    id = R.drawable.logo_dark
                ),
                contentScale = ContentScale.Fit,
                contentDescription = null,
                modifier = Modifier.weight(1f)
            )
            GigaGuideMobileTheme {
                Button(
                    onClick = {},
                    contentPadding = PaddingValues(6.dp),
                    modifier = Modifier
                        .padding(start = 30.dp)
                        .fillMaxHeight()
                        .aspectRatio(1f)
                        .dropShadow(
                            shape = RoundedCornerShape(10.dp),
                            offsetY = 0.dp,
                            offsetX = 0.dp,
                            blur = 16.dp,
                            color = MediumBlue.copy(0.9f)
                        ),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.secondary
                    )
                ) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "search",
                        modifier = Modifier
                            .clickable(onClick = {
                                navController.navigate(
                                    SearchScreenObject
                                )
                            })
                            .fillMaxSize()
                    )
                }
            }


        }

        Row(
            modifier = Modifier.padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                stringResource(R.string.home_screen_closest_to_you),
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onBackground
            )
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.chevron_right),
                modifier = Modifier.size(40.dp),
                contentDescription = "right",
                tint = MaterialTheme.colorScheme.onBackground
            )
        }

        Row(
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .padding(vertical = 20.dp)
        ) {
            if (homeScreenViewModel.loading.value) {
                for (i in 0..2) {
                    Spacer(Modifier.width(20.dp))
                    LoadingThumbnailBox(modifier = Modifier.width(275.dp))
                }
            } else {
                for (sight in homeScreenViewModel.closestTours) {
                    Spacer(Modifier.width(20.dp))
                    SightTourThumbnailBox(
                        modifier = Modifier
                            .width(275.dp)
                            .clickable(onClick = {
                                navController.navigate(
                                    SightPageScreenClass(sightId = sight.sightId)
                                )
                            }), value = sight
                    )
                }
            }
            Spacer(Modifier.width(20.dp))
        }


        Row(
            modifier = Modifier.padding(start = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                stringResource(R.string.home_screen_popular),
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onBackground
            )
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.chevron_right),
                modifier = Modifier.size(40.dp),
                contentDescription = "right",
                tint = MaterialTheme.colorScheme.onBackground
            )
        }

        Row(
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .padding(vertical = 20.dp)
        ) {
            if (homeScreenViewModel.loading.value) {
                for (i in 0..2) {
                    Spacer(Modifier.width(20.dp))
                    LoadingThumbnailBox(modifier = Modifier.width(275.dp))
                }
            } else {
                for (sight in homeScreenViewModel.popularTours) {
                    Spacer(Modifier.width(20.dp))
                    SightTourThumbnailBox(modifier = Modifier
                        .width(275.dp)
                        .clickable(onClick = {
                            navController.navigate(
                                SightPageScreenClass(sightId = sight.sightId)
                            )
                        }), value = sight)
                }
            }
            Spacer(Modifier.width(20.dp))
        }

    }

}