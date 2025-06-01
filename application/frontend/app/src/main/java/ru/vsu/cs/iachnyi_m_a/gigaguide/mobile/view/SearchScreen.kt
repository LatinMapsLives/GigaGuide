package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.R
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.sight.SightTourThumbnail
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.navigation.SightPageScreenClass
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.navigation.TourPageScreenClass
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.ui.theme.GigaGuideMobileTheme
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.view.util.dropShadow
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.viewmodel.SearchScreenViewModel

@OptIn(ExperimentalLayoutApi::class)
@Preview
@Composable
fun SearchScreen(
    searchScreenViewModel: SearchScreenViewModel = hiltViewModel<SearchScreenViewModel>(),
    navController: NavController = rememberNavController()
) {
    LaunchedEffect(Unit) {
        searchScreenViewModel.loadSearchResult()
    }
    GigaGuideMobileTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.weight(1f)) {
                    CustomTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = searchScreenViewModel.searchBarValue,
                        onValueChange = {searchScreenViewModel.searchBarValue = it},
                        hint = stringResource(R.string.search_screen_bar_hint),
                        isPassword = false
                    )
                    Icon(
                        modifier = Modifier
                            .clickable(onClick = {searchScreenViewModel.loadSearchResult()}, enabled = !searchScreenViewModel.loading)
                            .align(alignment = Alignment.CenterEnd)
                            .padding(10.dp)
                            .size(30.dp),
                        contentDescription = null,
                        imageVector = Icons.Filled.Search,
                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
                Button(
                    onClick = { navController.popBackStack() },
                    contentPadding = PaddingValues(6.dp),
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .height(54.dp)
                        .aspectRatio(1f),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.secondary
                    ),
                ) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        modifier = Modifier.size(20.dp),
                        imageVector = ImageVector.vectorResource(R.drawable.sort),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = stringResource(R.string.search_bar_sort),
                        style = MaterialTheme.typography.labelLarge,
                        modifier = Modifier.padding(start = 10.dp),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = stringResource(R.string.search_screen_filters),
                        style = MaterialTheme.typography.labelLarge,
                        modifier = Modifier.padding(end = 10.dp),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Icon(
                        modifier = Modifier.size(20.dp),
                        imageVector = ImageVector.vectorResource(R.drawable.settings2_svgrepo_com),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
            Text(
                style = MaterialTheme.typography.headlineMedium, text = stringResource(R.string.search_screen_search_result),
                color = MaterialTheme.colorScheme.onBackground
            )
            FlowRow(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.padding(vertical = 15.dp)
            ) {
                if (searchScreenViewModel.sightResult.isNotEmpty()) {
                    for (thumbnail in searchScreenViewModel.sightResult) {
                        SightTourSearchResult(
                            modifier = Modifier
                                .clickable(onClick = {
                                    navController.navigate(
                                        SightPageScreenClass(thumbnail.sightId)
                                    )
                                })
                                .fillMaxWidth(0.5f)
                                .padding(start = 5.dp, end = 5.dp, bottom = 10.dp),
                            sightTourThumbnail = thumbnail
                        )
                    }
                    for (thumbnail in searchScreenViewModel.tourResult) {
                        SightTourSearchResult(
                            modifier = Modifier
                                .clickable(onClick = {
                                    navController.navigate(
                                        TourPageScreenClass(thumbnail.sightId)
                                    )
                                })
                                .fillMaxWidth(0.5f)
                                .padding(start = 5.dp, end = 5.dp, bottom = 10.dp),
                            sightTourThumbnail = thumbnail
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SightTourSearchResult(modifier: Modifier, sightTourThumbnail: SightTourThumbnail) {
    Column(modifier = modifier) {
        AsyncImage(
            modifier = Modifier
                .dropShadow(offsetY = 0.dp, offsetX = 0.dp, blur = 16.dp)
                .clip(RoundedCornerShape(10.dp))
                .fillMaxWidth()
                .aspectRatio(180 / 110f),
            contentDescription = null,
            model = sightTourThumbnail.imageLink,
            contentScale = ContentScale.Crop
        )
        Row(
            verticalAlignment = Alignment.Top,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(0.75f),
                text = sightTourThumbnail.name,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onBackground
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                    tint = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    style = MaterialTheme.typography.labelSmall,
                    text = sightTourThumbnail.rating.toString(),
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}