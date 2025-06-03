package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.R
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.sight.SightTourThumbnail
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.navigation.LoginScreenObject
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.navigation.SightPageScreenClass
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.navigation.TourPageScreenClass
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.ui.theme.GigaGuideMobileTheme
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.ui.theme.MediumBlue
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.ui.theme.White
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.view.util.dropShadow
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.viewmodel.FavoritesScreenViewModel


@Composable
fun FavoritesScreen(
    favoritesScreenViewModel: FavoritesScreenViewModel = hiltViewModel<FavoritesScreenViewModel>(),
    navController: NavController
) {

    LaunchedEffect(Unit) {
        favoritesScreenViewModel.loadFavorites()
    }

    GigaGuideMobileTheme {
        if (!favoritesScreenViewModel.isAuthorized.value) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.background(
                    MaterialTheme.colorScheme.background
                )
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.bookmark),
                    tint = MaterialTheme.colorScheme.secondary,
                    contentDescription = "favorite icon",
                    modifier = Modifier
                        .padding(20.dp)
                        .size(100.dp)
                )
                Text(
                    text = stringResource(R.string.favorite_screen_label_log_in_to_use),
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center
                )
                Button(
                    modifier = Modifier
                        .padding(vertical = 20.dp)
                        .dropShadow(
                            offsetX = 0.dp,
                            offsetY = 0.dp,
                            blur = 16.dp,
                            shape = RoundedCornerShape(20.dp),
                            color = MediumBlue
                        ),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary,
                        contentColor = White
                    ),
                    onClick = { navController.navigate(LoginScreenObject) }
                ) {
                    Text(
                        text = stringResource(R.string.favorite_screen_log_in_button_text),
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.padding(vertical = 5.dp, horizontal = 60.dp)
                    )
                }
            }

        } else {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
                    .background(
                        MaterialTheme.colorScheme.background
                    )
                    .padding(20.dp)
            ) {
                Text(
                    text = stringResource(R.string.favorite_screen_header),
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Left,
                    modifier = Modifier.fillMaxWidth()
                )

                Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                    if (favoritesScreenViewModel.loading.value) {
                        for (i in 0..2) {
                            Spacer(modifier = Modifier.height(30.dp))
                            LoadingThumbnailBox(modifier = Modifier.fillMaxWidth())
                        }
                    } else if (!(favoritesScreenViewModel.favoriteSightThumbnails.isEmpty() && favoritesScreenViewModel.favoriteTourThumbnails.isEmpty())) {
                        for (thumbnail in favoritesScreenViewModel.favoriteSightThumbnails) {
                            Spacer(modifier = Modifier.height(30.dp))
                            SightTourThumbnailBoxSimple(
                                modifier = Modifier
                                    .clickable(onClick = {
                                        navController.navigate(
                                            SightPageScreenClass(thumbnail.sightId)
                                        )
                                    })
                                    .fillMaxWidth(),
                                value = thumbnail
                            )
                        }
                        for (thumbnail in favoritesScreenViewModel.favoriteTourThumbnails) {
                            Spacer(modifier = Modifier.height(30.dp))
                            SightTourThumbnailBoxSimple(
                                modifier = Modifier
                                    .clickable(onClick = {
                                        navController.navigate(
                                            TourPageScreenClass(thumbnail.sightId)
                                        )
                                    })
                                    .fillMaxWidth(),
                                value = thumbnail
                            )
                        }
                    } else {
                        Text(
                            text = stringResource(R.string.favorite_screen_header_empty),
                            modifier = Modifier.padding(top = 20.dp),
                            style = MaterialTheme.typography.headlineSmall,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                    Spacer(modifier = Modifier.height(100.dp))
                }
            }
        }
    }


}

@Composable
fun SightTourThumbnailBoxSimple(modifier: Modifier, value: SightTourThumbnail) {

    val cornerSize = 10.dp
    GigaGuideMobileTheme {

        Box(
            modifier = modifier
                .dropShadow(
                    offsetX = 0.dp,
                    offsetY = 0.dp,
                    blur = 16.dp,
                    shape = RoundedCornerShape(cornerSize)
                ),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 2.dp,
                        color = MaterialTheme.colorScheme.secondary,
                        shape = RoundedCornerShape(cornerSize)
                    )
                    .clip(RoundedCornerShape(cornerSize))
                    .background(color = MaterialTheme.colorScheme.background)
            ) {
                Spacer(
                    modifier = Modifier
                        .aspectRatio(300f / 135)
                        .fillMaxWidth(),
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = MaterialTheme.colorScheme.background),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    var paddingLeft = 15.dp

                    Text(
                        value.name,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(paddingValues = PaddingValues(start = paddingLeft, top = paddingLeft, bottom = paddingLeft)),
                        color = MaterialTheme.colorScheme.onBackground
                    )

                }
            }
            AsyncImage(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.tertiary)
                    .aspectRatio(300f / 135)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(topStart = cornerSize, topEnd = cornerSize)),
                contentScale = ContentScale.Crop,
                contentDescription = null,
                model = value.imageLink,
            )
        }


    }

}