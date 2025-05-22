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
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Star
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.R
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.sight.SightTourThumbnail
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.navigation.ExploreTourScreenClass
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.navigation.LoginScreenObject
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.navigation.ReviewScreenClass
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.ui.theme.Black
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.ui.theme.FavoritePink
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.ui.theme.GigaGuideMobileTheme
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.ui.theme.MediumGrey
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.ui.theme.White
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.ui.theme.Yellow
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.view.util.dropShadow
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.viewmodel.TourPageScreenViewModel

@Composable
fun TourPageScreen(
    tourId: Long,
    navController: NavController,
    tourPageScreenViewModel: TourPageScreenViewModel = hiltViewModel<TourPageScreenViewModel>()
) {

    LaunchedEffect(Unit) {
        tourPageScreenViewModel.tourId = tourId
        tourPageScreenViewModel.loadTour()
        tourPageScreenViewModel.loadFavoriteData()
    }
    var loadingColor = MaterialTheme.colorScheme.tertiary
    if (tourPageScreenViewModel.loading.value || tourPageScreenViewModel.tour.value == null) {
        LoadingScreen(navController)
    } else {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.background)
            ) {
                var aspectRation: Float = 410f / 290
                Box(
                    modifier = Modifier
                        .background(color = loadingColor)
                        .fillMaxWidth()
                        .aspectRatio(aspectRation)
                ) {
                    AsyncImage(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.tertiary)
                            .fillMaxWidth()
                            .aspectRatio(aspectRation),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        model = tourPageScreenViewModel.tour.value!!.imageLink
                    )

                    Column(
                        modifier = Modifier
                            .align(alignment = Alignment.BottomCenter)
                            .background(
                                brush = Brush.verticalGradient(
                                    colorStops = arrayOf(
                                        0.0f to Black.copy(0f), 1f to Black
                                    )
                                )
                            )
                            .fillMaxWidth()
                            .padding(10.dp),
                        verticalArrangement = Arrangement.Bottom,
                        horizontalAlignment = Alignment.Start
                    ) {

                        Text(
                            modifier = Modifier.fillMaxWidth(0.75f),
                            style = MaterialTheme.typography.headlineMedium,
                            text = tourPageScreenViewModel.tour.value!!.name,
                            color = White
                        )


                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Text(
                                text = "3 места на маршруте",
                                color = White
                            )

                            Row {

                                Text(
                                    text = "${tourPageScreenViewModel.tour.value!!.distanceKm} км",
                                    color = White
                                )

                                Icon(
                                    modifier = Modifier.padding(horizontal = 10.dp),
                                    imageVector = ImageVector.vectorResource(R.drawable.tour_length),
                                    tint = White,
                                    contentDescription = "time"
                                )
                            }
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Row {
                                Icon(
                                    modifier = Modifier.size(24.dp),
                                    imageVector = ImageVector.vectorResource(R.drawable.walking_man),
                                    tint = White,
                                    contentDescription = null
                                )

                                Text(
                                    text = tourPageScreenViewModel.tour.value!!.type,
                                    color = White
                                )
                            }

                            Row {

                                Text(
                                    text = tourPageScreenViewModel.tour.value!!.category,
                                    color = White
                                )

                                Icon(
                                    modifier = Modifier
                                        .padding(horizontal = 8.dp)
                                        .size(24.dp),
                                    imageVector = ImageVector.vectorResource(R.drawable.book),
                                    tint = White,
                                    contentDescription = null
                                )
                            }
                        }
                    }
                }
                Column(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(bottom = 10.dp)
                            .clickable(onClick = {
                                navController.navigate(
                                    ReviewScreenClass(tourId)
                                )
                            })
                    ) {
                        Text(
                            text = "16 отзывов | 4.8",
                            style = MaterialTheme.typography.headlineSmall,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Icon(
                            imageVector = Icons.Filled.Star,
                            tint = Yellow,
                            modifier = Modifier.size(30.dp),
                            contentDescription = null
                        )
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.chevron_right),
                            contentDescription = null,
                            modifier = Modifier.size(40.dp),
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = stringResource(R.string.sight_page_screen_header_description),
                            style = MaterialTheme.typography.headlineSmall,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Icon(
                            modifier = Modifier
                                .padding(horizontal = 10.dp)
                                .width(30.dp),
                            imageVector = ImageVector.vectorResource(R.drawable.chevron_down),
                            tint = MaterialTheme.colorScheme.onBackground,
                            contentDescription = "chevron down"
                        )
                    }

                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 20.dp),
                        text = tourPageScreenViewModel.tour.value!!.description,
                        color = MaterialTheme.colorScheme.onBackground
                    )


                    Row(
                        modifier = Modifier.padding(bottom = 20.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Достопримечательности",
                            style = MaterialTheme.typography.headlineSmall,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Icon(
                            modifier = Modifier
                                .padding(horizontal = 10.dp)
                                .width(30.dp),
                            imageVector = Icons.Filled.Place,
                            tint = MaterialTheme.colorScheme.onBackground,
                            contentDescription = null
                        )
                    }

                    Spacer(modifier = Modifier.height(50.dp))
                }
            }

            Button(
                onClick = { navController.popBackStack() },
                contentPadding = PaddingValues(6.dp),
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(20.dp)
                    .defaultMinSize(minWidth = 1.dp, minHeight = 1.dp)
                    .height(50.dp)
                    .aspectRatio(1f),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.secondary
                ),
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.chevron_left),
                    contentDescription = "search",
                    modifier = Modifier.fillMaxSize()
                )
            }

            RoundedCornerSquareButton(
                modifier = Modifier
                    .padding(20.dp)
                    .align(Alignment.TopEnd)
                    .size(50.dp),
                onClick = {
                    if (!tourPageScreenViewModel.loadingFavorite) {
                        if(tourPageScreenViewModel.token == null){
                            navController.navigate(LoginScreenObject)
                        } else {
                            if (tourPageScreenViewModel.inFavorite.value) {
                                tourPageScreenViewModel.deleteFromFavorite()
                            } else{
                                tourPageScreenViewModel.addToFavorite()
                            }
                        }
                    }
                },
                imageVector = if (tourPageScreenViewModel.inFavorite.value) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                contentColor = if (tourPageScreenViewModel.inFavorite.value) FavoritePink else MediumGrey
            )

            Button(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary, contentColor = White
                ),
                onClick = {navController.navigate(ExploreTourScreenClass(tourId))}
            ) {
                Text(
                    text = stringResource(R.string.tour_screen_button_explore_tour),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(vertical = 5.dp, horizontal = 30.dp),
                    color = White
                )
            }
        }
    }
}

@Composable
fun SightListItem(
    modifier: Modifier = Modifier, value: SightTourThumbnail = SightTourThumbnail(
        sightId = 0,
        name = "Тур по Воронежу",
        rating = 4.5f,
        proximity = 0f,
        imageLink = "https://vestivrn.ru/media/archive/image/2024/05/LT7zhWmmZ-tD6ilZ4nQcUPkF1S4SIdph.jpg"
    ),
    number: Int = 1
) {
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
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxSize()
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
                        .aspectRatio(110f / 70)
                        .fillMaxWidth(),
                )
                Text(
                    modifier = Modifier
                        .weight(1f)
                        .padding(10.dp),
                    text = "${number}. ${value.name}",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Icon(
                    modifier = Modifier
                        .size(40.dp),
                    tint = MaterialTheme.colorScheme.onBackground,
                    contentDescription = null,
                    imageVector = ImageVector.vectorResource(R.drawable.chevron_right)
                )
            }
            AsyncImage(
                modifier = Modifier
                    .clip(RoundedCornerShape(topStart = cornerSize, bottomStart = cornerSize))
                    .background(MaterialTheme.colorScheme.primary)
                    .aspectRatio(110f / 70)
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop,
                contentDescription = null,
                model = value.imageLink,
            )
        }

    }
}