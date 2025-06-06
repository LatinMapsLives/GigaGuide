package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.navigation.ExploreSightScreenClass
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.navigation.LoginScreenObject
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.navigation.SightReviewScreenClass
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.ui.theme.Black
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.ui.theme.FavoritePink
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.ui.theme.MediumGrey
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.ui.theme.White
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.ui.theme.Yellow
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.viewmodel.SightPageScreenViewModel

@Composable
fun SightPageScreen(
    modifier: Modifier = Modifier,
    sightId: Long = 0,
    sightPageScreenViewModel: SightPageScreenViewModel = hiltViewModel<SightPageScreenViewModel>(),
    navController: NavController,
) {
    LaunchedEffect(Unit) {
        sightPageScreenViewModel.sightId = sightId
        sightPageScreenViewModel.loadSight()
        sightPageScreenViewModel.loadFavoriteData()
    }
    val loadingColor = MaterialTheme.colorScheme.tertiary
    if (sightPageScreenViewModel.loading.value || sightPageScreenViewModel.sight.value == null) {
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
                        contentDescription = "Tour image",
                        contentScale = ContentScale.Crop,
                        model = sightPageScreenViewModel.sight.value!!.imageLink
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
                            text = sightPageScreenViewModel.sight.value!!.name,
                            color = White
                        )


                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Text(
                                text = "${sightPageScreenViewModel.momentNames.size} ${
                                    stringResource(
                                        R.string.sight_page_screen_moments_count
                                    )
                                }",
                                color = White
                            )
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
                                    SightReviewScreenClass(sightId)
                                )
                            })
                    ) {
                        Text(
                            text = "${sightPageScreenViewModel.reviewCount} ${stringResource(R.string.sight_page_screen_review_count)} | ${sightPageScreenViewModel.rating}",
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
                    var descVisible by remember { mutableStateOf(true) }
                    Row(modifier = Modifier.padding(bottom = 20.dp), verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = stringResource(R.string.sight_page_screen_header_description),
                            style = MaterialTheme.typography.headlineSmall,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Icon(
                            modifier = Modifier
                                .clickable(onClick = {descVisible = !descVisible})
                                .padding(horizontal = 10.dp)
                                .size(30.dp),
                            imageVector = ImageVector.vectorResource(if (descVisible) R.drawable.chevron_down else R.drawable.chevron_right_big),
                            tint = MaterialTheme.colorScheme.onBackground,
                            contentDescription = "chevron down"
                        )
                    }
                    AnimatedVisibility(modifier = Modifier.fillMaxWidth(), visible = descVisible) {
                        Text(
                            modifier = Modifier
                                .padding(bottom = 20.dp)
                                .fillMaxWidth(),
                            text = sightPageScreenViewModel.sight.value!!.description,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                    Row(
                        modifier = Modifier.padding(bottom = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(R.string.sight_page_screen_header_moments),
                            style = MaterialTheme.typography.headlineSmall,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }


                    sightPageScreenViewModel.momentNames.forEachIndexed { i, s ->

                        Text(
                            modifier = Modifier.padding(bottom = 5.dp),
                            style = MaterialTheme.typography.titleSmall,
                            text = "${i + 1}. ${s}",
                            color = MaterialTheme.colorScheme.onBackground
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
                    if (!sightPageScreenViewModel.loadingFavorite) {
                        if (sightPageScreenViewModel.token == null) {
                            navController.navigate(LoginScreenObject)
                        } else {
                            if (sightPageScreenViewModel.inFavorite.value) {
                                sightPageScreenViewModel.deleteFromFavorite()
                            } else {
                                sightPageScreenViewModel.addToFavorite()
                            }
                        }
                    }
                },
                imageVector = if (sightPageScreenViewModel.inFavorite.value) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                contentColor = if (sightPageScreenViewModel.inFavorite.value) FavoritePink else MediumGrey
            )

            Button(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary, contentColor = White
                ),
                onClick = { navController.navigate(ExploreSightScreenClass(sightId = sightPageScreenViewModel.sightId)) }) {
                Text(
                    text = stringResource(R.string.sight_page_screen_button_explore_sight),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(vertical = 5.dp, horizontal = 30.dp),
                    color = White
                )
            }
        }
    }
}

@Composable
fun LoadingScreen(navController: NavController) {

    var loadingColor = MaterialTheme.colorScheme.tertiary
    var spacerCornerSize = 5.dp
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {


        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background)
        ) {
            var aspectRation: Float = 410f / 290
            Box(
                modifier = Modifier
                    .background(color = MaterialTheme.colorScheme.tertiary)
                    .fillMaxWidth()
                    .aspectRatio(aspectRation)
            ) {
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
                    Spacer(
                        modifier = Modifier
                            .padding(bottom = 10.dp)
                            .clip(RoundedCornerShape(spacerCornerSize))
                            .background(loadingColor)
                            .fillMaxWidth(0.75f)
                            .height(30.dp)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Spacer(
                            modifier = Modifier
                                .clip(RoundedCornerShape(spacerCornerSize))
                                .background(loadingColor)
                                .width(70.dp)
                                .height(30.dp)
                        )


                        Row {
                            Spacer(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(spacerCornerSize))

                                    .background(color = loadingColor)
                                    .width(70.dp)
                                    .height(30.dp)
                            )

                            Icon(
                                modifier = Modifier.padding(horizontal = 10.dp),
                                imageVector = ImageVector.vectorResource(R.drawable.time),
                                tint = White,
                                contentDescription = "time"
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
                        imageVector = ImageVector.vectorResource(R.drawable.chevron_down_flat),
                        tint = MaterialTheme.colorScheme.onBackground,
                        contentDescription = "chevron down"
                    )
                }
                Spacer(
                    Modifier
                        .padding(vertical = 20.dp)
                        .clip(RoundedCornerShape(spacerCornerSize))
                        .background(loadingColor)
                        .fillMaxWidth()
                        .height(20.dp)
                )
                for (i in 0..4) {
                    Spacer(
                        Modifier
                            .padding(bottom = 20.dp)
                            .clip(RoundedCornerShape(spacerCornerSize))
                            .background(loadingColor)
                            .fillMaxWidth()
                            .height(20.dp)
                    )
                }


                Row(
                    modifier = Modifier.padding(bottom = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.sight_page_screen_header_moments),
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Icon(
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                            .width(30.dp),
                        imageVector = ImageVector.vectorResource(R.drawable.moments),
                        tint = MaterialTheme.colorScheme.onBackground,
                        contentDescription = "moments"
                    )
                }

                Spacer(
                    modifier = Modifier
                        .clip(
                            RoundedCornerShape(spacerCornerSize)
                        )
                        .width(50.dp)
                        .height(20.dp)
                        .background(color = loadingColor)
                )



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
            onClick = {},
            Icons.Filled.FavoriteBorder,
            contentColor = MediumGrey
        )
    }
}