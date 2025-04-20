package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.R
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.ui.theme.Black
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.ui.theme.LightGrey
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.ui.theme.White
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.viewmodel.SightPageScreenViewModel

@Composable
fun SightPageScreen(
    modifier: Modifier = Modifier,
    sightId: Long = 0,
    sightPageScreenViewModel: SightPageScreenViewModel = SightPageScreenViewModel(),
    navController: NavController
) {
    if (sightPageScreenViewModel.sightId.longValue != sightId) {
        sightPageScreenViewModel.sightId.longValue = sightId
        sightPageScreenViewModel.loadSight()
    }

    val loadingColor = LightGrey
    val spacerCornerSize = 5.dp

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
                if (!sightPageScreenViewModel.loading.value) {
                    Image(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(aspectRation),
                        painter = painterResource(R.drawable.jonkler),
                        contentDescription = "Tour image",
                        contentScale = ContentScale.Crop
                    )
                }
                Column(
                    modifier = Modifier
                        .align(alignment = Alignment.BottomCenter)
                        .background(
                            brush = Brush.verticalGradient(
                                colorStops = arrayOf(
                                    0.0f to Black.copy(0f),
                                    1f to Black
                                )
                            )
                        )
                        .fillMaxWidth()
                        .padding(10.dp),
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.Start
                ) {
                    if (sightPageScreenViewModel.loading.value) {
                        Spacer(
                            modifier = Modifier
                                .clip(RoundedCornerShape(spacerCornerSize))
                                .background(loadingColor)
                                .fillMaxWidth(0.75f)
                                .height(30.dp)
                        )
                    } else {
                        Text(
                            modifier = Modifier.fillMaxWidth(0.75f),
                            style = MaterialTheme.typography.headlineMedium,
                            text = sightPageScreenViewModel.sight.value!!.name,
                            color = White
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (sightPageScreenViewModel.loading.value) {
                            Spacer(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(spacerCornerSize))
                                    .background(loadingColor)
                                    .width(70.dp)
                                    .height(30.dp)
                            )
                        } else {
                            Text(
                                text = "${sightPageScreenViewModel.sight.value!!.momentNames.size} моментов",
                                color = White
                            )
                        }

                        Row {
                            if (sightPageScreenViewModel.loading.value) {
                                Spacer(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(spacerCornerSize))

                                        .background(color = loadingColor)
                                        .width(70.dp)
                                        .height(30.dp)
                                )
                            } else {
                                Text(
                                    text = "${sightPageScreenViewModel.sight.value!!.time} мин",
                                    color = White
                                )
                            }

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
                    )
                    Icon(
                        modifier = Modifier
                            .width(30.dp)
                            .padding(horizontal = 20.dp),
                        imageVector = ImageVector.vectorResource(R.drawable.chevron_down),
                        tint = MaterialTheme.colorScheme.onBackground,
                        contentDescription = "chevron down"
                    )
                }
                if (sightPageScreenViewModel.loading.value) {
                    Spacer(
                        modifier
                            .padding(vertical = 20.dp)
                            .clip(RoundedCornerShape(spacerCornerSize))
                            .background(loadingColor)
                            .fillMaxWidth()
                            .height(50.dp)
                    )
                } else {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 20.dp),
                        text = sightPageScreenViewModel.sight.value!!.description
                    )
                }

                Row(
                    modifier = Modifier.padding(bottom = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.sight_page_screen_header_moments),
                        style = MaterialTheme.typography.headlineSmall
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

                if (sightPageScreenViewModel.loading.value) {
                    Spacer(
                        modifier = Modifier
                            .clip(
                                RoundedCornerShape(spacerCornerSize)
                            )
                            .width(50.dp)
                            .height(20.dp)
                            .background(color = loadingColor)

                    )
                } else {
                    sightPageScreenViewModel.sight.value!!.momentNames.forEachIndexed { i, s ->

                        Text(
                            modifier = Modifier.padding(bottom = 5.dp),
                            style = MaterialTheme.typography.titleSmall,
                            text = "${i + 1}. ${s}"
                        )

                    }
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

        Button(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(20.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = White
            ),
            onClick = {}
        ) {
            Text(
                text = stringResource(R.string.sight_page_screen_button_explore_sight),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(vertical = 5.dp, horizontal = 30.dp)
            )
        }
    }


}