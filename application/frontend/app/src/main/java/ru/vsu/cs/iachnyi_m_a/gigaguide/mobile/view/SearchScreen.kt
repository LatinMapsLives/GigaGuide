package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.R
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.sight.SightTourThumbnail
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.navigation.SightPageScreenClass
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.navigation.TourPageScreenClass
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.ui.theme.GigaGuideMobileTheme
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.ui.theme.MediumBlue
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.ui.theme.Red
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.ui.theme.White
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.view.util.dropShadow
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.viewmodel.SearchScreenViewModel
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.viewmodel.SortingOptions

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
    var sortDialogOpen by remember { mutableStateOf(false) }
    var filterDialogOpen by remember { mutableStateOf(false) }
    GigaGuideMobileTheme {
        when {
            sortDialogOpen -> {
                Dialog(onDismissRequest = {sortDialogOpen = false}) {
                    Column(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(color = MaterialTheme.colorScheme.background)
                            .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = stringResource(R.string.search_screen_sorting_header),
                            color = MaterialTheme.colorScheme.onBackground,
                            style = MaterialTheme.typography.titleLarge
                        )
                        var currentOption by
                        remember { mutableStateOf(SortingOptions.valueOf(searchScreenViewModel.sortingOptions.name)) }
                        val radioOptions = listOf(
                            SortingOptions.NONE, SortingOptions.RATING,
                            SortingOptions.REMOTENESS
                        )
                        val radioLabels = listOf(
                            stringResource(R.string.search_screen_sorting_option_none),
                            stringResource(R.string.search_screen_sorting_option_rating),
                            stringResource(R.string.search_screen_sorting_option_remoteness)
                        )
                        Column(Modifier.selectableGroup()) {
                            radioOptions.forEachIndexed { i, option ->
                                Row(
                                    Modifier
                                        .fillMaxWidth()
                                        .selectable(
                                            selected = (option == currentOption),
                                            onClick = {
                                                currentOption = option
                                            },
                                            role = Role.RadioButton
                                        )
                                        .padding(top = 10.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    RadioButton(
                                        selected = (option == currentOption),
                                        onClick = null
                                    )
                                    Text(
                                        color = MaterialTheme.colorScheme.onBackground,
                                        text = radioLabels[i],
                                        style = MaterialTheme.typography.bodyLarge,
                                        modifier = Modifier.padding(start = 16.dp)
                                    )
                                }
                            }
                        }
                        Row {
                            Button(
                                onClick = { sortDialogOpen = false },
                                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 5.dp),
                                colors = ButtonDefaults.buttonColors(
                                    contentColor = White,
                                    containerColor = MediumBlue
                                )
                            ) {
                                Text(stringResource(R.string.search_screen_sort_dialog_cancel))
                            }
                            Button(
                                modifier = Modifier.padding(start = 10.dp),
                                onClick = {
                                    searchScreenViewModel.sortingOptions = currentOption
                                    searchScreenViewModel.loadSearchResult()
                                    sortDialogOpen = false
                                },
                                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 5.dp),
                                colors = ButtonDefaults.buttonColors(
                                    contentColor = White,
                                    containerColor = MediumBlue
                                )
                            ) {
                                Text(stringResource(R.string.search_screen_sort_dialog_apply))
                            }
                        }
                    }
                }
            }
        }
        when {
            filterDialogOpen -> {
                Dialog(onDismissRequest = {filterDialogOpen = false}) {
                    Column(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(color = MaterialTheme.colorScheme.background)
                            .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = stringResource(R.string.search_screen_filter_dialog_headers),
                            color = MaterialTheme.colorScheme.onBackground,
                            style = MaterialTheme.typography.titleLarge
                        )

                        var searchTours by remember { mutableStateOf(searchScreenViewModel.searchTours) }
                        var searchSights by remember { mutableStateOf(searchScreenViewModel.searchSights) }

                        Row (modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically){
                            Box(modifier = Modifier.size(35.dp), contentAlignment = Alignment.Center){
                                Checkbox(checked = searchSights, onCheckedChange = {searchSights = it})
                            }
                            Text(modifier = Modifier.padding(start = 5.dp).clickable(onClick = {searchSights = !searchSights}), text = stringResource(R.string.search_screen_filter_search_sights), color = MaterialTheme.colorScheme.onBackground)
                        }

                        Row (modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically){
                            Box(modifier = Modifier.size(35.dp), contentAlignment = Alignment.Center){
                                Checkbox(checked = searchTours, onCheckedChange = {searchTours = it})
                            }
                            Text(modifier = Modifier.padding(start = 5.dp).clickable(onClick = {searchTours = !searchTours}), text = stringResource(R.string.search_screen_filter_search_tours), color = MaterialTheme.colorScheme.onBackground)
                        }

                        var newMinDuration by remember { mutableIntStateOf(searchScreenViewModel.minDuration) }
                        var newMaxDuration by remember { mutableIntStateOf(searchScreenViewModel.maxDuration) }
                        var newMinDistance by remember { mutableDoubleStateOf(searchScreenViewModel.minDistance) }
                        var newMaxDistance by remember { mutableDoubleStateOf(searchScreenViewModel.maxDistance) }

                        AnimatedVisibility(visible = searchTours) {
                            Column (modifier = Modifier.fillMaxWidth()){
                                Text(color = MaterialTheme.colorScheme.onBackground, text = stringResource(R.string.search_screen_filter_tours_header), style = MaterialTheme.typography.titleMedium)
                                Text(color = MaterialTheme.colorScheme.onBackground, text = stringResource(R.string.search_screen_filters_duration))
                                Row (verticalAlignment = Alignment.CenterVertically){
                                    Text(color = MaterialTheme.colorScheme.onBackground, modifier = Modifier.weight(1f), text = stringResource(R.string.search_screen_filters_from))
                                    CustomTextField(modifier = Modifier.weight(2f), value = newMinDuration.toString(), onValueChange = {
                                        try {
                                            val int = it.toInt()
                                            if (int >= 0) newMinDuration = int
                                        } catch (e: Exception){

                                        }
                                    }, hint = "")
                                    Text(color = MaterialTheme.colorScheme.onBackground, modifier = Modifier.weight(1f), text = stringResource(R.string.search_screen_filters_to))
                                    CustomTextField(modifier = Modifier.weight(2f), value = newMaxDuration.toString(), onValueChange = {
                                        try {
                                            val int = it.toInt()
                                            if(int >= 0) newMaxDuration = int
                                        } catch (e: Exception){

                                        }
                                    }, hint = "")
                                    Text(color = MaterialTheme.colorScheme.onBackground, modifier = Modifier.weight(1f), text = stringResource(R.string.sight_page_screen_minutes))
                                }
                                Text(color = MaterialTheme.colorScheme.onBackground, text = stringResource(R.string.search_screen_filters_length))
                                Row (verticalAlignment = Alignment.CenterVertically){
                                    Text(color = MaterialTheme.colorScheme.onBackground, modifier = Modifier.weight(1f), text = stringResource(R.string.search_screen_filters_from))
                                    CustomTextField(modifier = Modifier.weight(2f), value = newMinDistance.toString(), onValueChange = {
                                        try {
                                            val int = it.toDouble()
                                            if(int >= 0) newMinDistance = int
                                        } catch (e: Exception){

                                        }
                                    }, hint = "")
                                    Text(color = MaterialTheme.colorScheme.onBackground, modifier = Modifier.weight(1f), text = stringResource(R.string.search_screen_filters_to))
                                    CustomTextField(modifier = Modifier.weight(2f), value = newMaxDistance.toString(), onValueChange = {
                                        try {
                                            val int = it.toDouble()
                                            if(int >= 0) newMaxDistance = int
                                        } catch (e: Exception){

                                        }
                                    }, hint = "")
                                    Text(color = MaterialTheme.colorScheme.onBackground, modifier = Modifier.weight(1f), text = "км")
                                }
                            }
                        }

                        Row {
                            Button(
                                onClick = { filterDialogOpen = false },
                                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 5.dp),
                                colors = ButtonDefaults.buttonColors(
                                    contentColor = White,
                                    containerColor = MediumBlue
                                )
                            ) {
                                Text(stringResource(R.string.search_screen_sort_dialog_cancel))
                            }
                            Button(
                                modifier = Modifier.padding(start = 10.dp),
                                onClick = {
                                    searchScreenViewModel.searchTours = searchTours
                                    searchScreenViewModel.searchSights = searchSights
                                    searchScreenViewModel.minDuration = newMinDuration
                                    searchScreenViewModel.maxDuration = newMaxDuration
                                    searchScreenViewModel.minDistance = newMinDistance
                                    searchScreenViewModel.maxDistance = newMaxDistance


                                    searchScreenViewModel.loadSearchResult()
                                    filterDialogOpen = false
                                },
                                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 5.dp),
                                colors = ButtonDefaults.buttonColors(
                                    contentColor = White,
                                    containerColor = MediumBlue
                                )
                            ) {
                                Text(stringResource(R.string.search_screen_sort_dialog_apply))
                            }
                        }
                    }
                }
            }
        }
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
                        onValueChange = { searchScreenViewModel.searchBarValue = it },
                        hint = stringResource(R.string.search_screen_bar_hint),
                        isPassword = false
                    )
                    Icon(
                        modifier = Modifier
                            .clickable(
                                onClick = { searchScreenViewModel.loadSearchResult() },
                                enabled = !searchScreenViewModel.loading
                            )
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
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable(onClick = { sortDialogOpen = true })
                ) {
                    Icon(
                        modifier = Modifier.size(20.dp),
                        imageVector = ImageVector.vectorResource(R.drawable.sort),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = stringResource(R.string.search_bar_sort),
                        style = MaterialTheme.typography.labelLarge,
                        modifier = Modifier.padding(horizontal = 5.dp),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    if (searchScreenViewModel.sortingOptions != SortingOptions.NONE) {
                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(color = Red)
                                .width(20.dp)
                                .height(20.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                textAlign = TextAlign.Center,
                                text = "1",
                                style = MaterialTheme.typography.bodySmall,
                                color = White
                            )
                        }
                    }
                }
                Row(modifier = Modifier.clickable(onClick = {filterDialogOpen = true}), verticalAlignment = Alignment.CenterVertically) {
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
                style = MaterialTheme.typography.headlineMedium,
                text = stringResource(R.string.search_screen_search_result),
                color = MaterialTheme.colorScheme.onBackground
            )
            FlowRow(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.padding(vertical = 15.dp)
            ) {
                if (!searchScreenViewModel.searchResult.isEmpty()) {
                    for (thumbnail in searchScreenViewModel.searchResult) {
                        SightTourSearchResult(
                            modifier = Modifier
                                .clickable(onClick = if(thumbnail.isTour){{
                                    navController.navigate(
                                        TourPageScreenClass(thumbnail.sightId)
                                    )
                                }} else {{navController.navigate(SightPageScreenClass(thumbnail.sightId))}})
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
                modifier = Modifier.weight(1f),
                text = sightTourThumbnail.name,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onBackground
            )
            Column(){
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
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Filled.Place,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        style = MaterialTheme.typography.labelSmall,
                        text = sightTourThumbnail.proximity.toString(),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }

        }
    }
}