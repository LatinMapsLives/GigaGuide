package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.R
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.SightTourThumbnail
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.ui.theme.GigaGuideMobileTheme
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.ui.theme.LightGrey
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.ui.theme.MediumBlue
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.view.util.dropShadow
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.viewmodel.HomeScreenViewModel

@Composable
fun HomeScreen(homeScreenViewModel: HomeScreenViewModel) {

    Column(
        Modifier
            .fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .height(94.dp)
                .padding(20.dp)
        ) {
            Image(
                imageVector = if (!isSystemInDarkTheme()) ImageVector.vectorResource(id = R.drawable.logo) else ImageVector.vectorResource(
                    id = R.drawable.logo_dark
                ),
                contentScale = ContentScale.Fit,
                contentDescription = "logo",
                modifier = Modifier.fillMaxHeight()
            )
            GigaGuideMobileTheme {
                Button(
                    onClick = {},
                    contentPadding = PaddingValues(6.dp),
                    modifier = Modifier
                        .defaultMinSize(minWidth = 1.dp, minHeight = 1.dp)
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

        var sights = homeScreenViewModel.closestTours
        if (sights.isEmpty()) homeScreenViewModel.loadClosestTours()

        Row(
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .padding(vertical = 20.dp)
        ) {
            if (homeScreenViewModel.loading.value) {
                for (i in 0..2) {
                    Spacer(Modifier.width(20.dp))
                    LoadingThumbnailBox()
                }
            } else {
                for (sight in sights) {
                    Spacer(Modifier.width(20.dp))
                    SightTourThumbnailBox(sight)
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
                    LoadingThumbnailBox()
                }
            } else {
                for (sight in sights) {
                    Spacer(Modifier.width(20.dp))
                    SightTourThumbnailBox(sight)
                }
            }
            Spacer(Modifier.width(20.dp))
        }

    }

}

@Composable
fun LoadingThumbnailBox() {
    var loadingColor: Color = LightGrey
    val cornerSize = 10.dp
    val spacerCornerSize = 5.dp
    GigaGuideMobileTheme {

        Box(
            modifier = Modifier
                .width(275.dp)
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

                    Spacer(modifier = Modifier.padding(paddingValues = PaddingValues(start = paddingLeft)))
                    Spacer(
                        modifier = Modifier
                            .width(150.dp)
                            .height(24.dp)
                            .clip(RoundedCornerShape(spacerCornerSize))
                            .background(color = loadingColor)
                    )

                    Column {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(PaddingValues(top = 4.dp, end = 4.dp, bottom = 2.dp)),
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Spacer(
                                modifier = Modifier
                                    .width(50.dp)
                                    .height(20.dp)
                                    .clip(RoundedCornerShape(spacerCornerSize))
                                    .background(color = loadingColor)
                            )


                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(PaddingValues(top = 2.dp, end = 4.dp, bottom = 4.dp)),
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Spacer(
                                modifier = Modifier
                                    .width(50.dp)
                                    .height(20.dp)
                                    .clip(RoundedCornerShape(spacerCornerSize))
                                    .background(color = loadingColor)
                            )

                        }
                    }
                }
            }
            Image(
                modifier = Modifier
                    .aspectRatio(300f / 135)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(topStart = cornerSize, topEnd = cornerSize)),
                painter = ColorPainter(loadingColor),
                contentScale = ContentScale.Crop,
                contentDescription = "jonkler",
            )
        }


    }
}

@Composable
fun SightTourThumbnailBox(value: SightTourThumbnail) {

    val cornerSize = 10.dp
    GigaGuideMobileTheme {

        Box(
            modifier = Modifier
                .width(275.dp)
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
                        modifier = Modifier.padding(paddingValues = PaddingValues(start = paddingLeft)),
                        color = MaterialTheme.colorScheme.onBackground
                    )


                    Column {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(PaddingValues(top = 4.dp, end = 4.dp, bottom = 2.dp)),
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Text(
                                value.rating.toString(),
                                style = MaterialTheme.typography.titleSmall,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                            Icon(
                                imageVector = Icons.Filled.Star,
                                modifier = Modifier.size(20.dp),
                                contentDescription = "star icon",
                                tint = MaterialTheme.colorScheme.onBackground
                            )


                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(PaddingValues(top = 2.dp, end = 4.dp, bottom = 4.dp)),
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Text(
                                value.proximity.toString(),
                                style = MaterialTheme.typography.titleSmall,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                            Icon(
                                imageVector = Icons.Outlined.Place,
                                modifier = Modifier.size(20.dp),
                                contentDescription = "proximity icon",
                                tint = MaterialTheme.colorScheme.onBackground
                            )

                        }
                    }
                }
            }
            Image(
                modifier = Modifier
                    .aspectRatio(300f / 135)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(topStart = cornerSize, topEnd = cornerSize)),
                painter = painterResource(R.drawable.jonkler),
                contentScale = ContentScale.Crop,
                contentDescription = "jonkler",
            )
        }


    }

}
