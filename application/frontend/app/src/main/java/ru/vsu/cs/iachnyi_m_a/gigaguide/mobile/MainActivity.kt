package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.SightTourThumbnail
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.ui.theme.GigaGuideMobileTheme
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.viewmodel.HomeViewModel


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GigaGuideMobileTheme {
                Main()
            }
        }
    }
}

@Preview
@Composable
fun Main() {

    Column(
        Modifier
            .fillMaxWidth()
    ) {
        HomeScreen()
    }

}

@Composable
fun HomeScreen() {
    remember { HomeViewModel() }

    Column(Modifier
        .fillMaxSize()) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .height(94.dp).
            padding(20.dp)
        ) {
            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.logo),
                contentScale = ContentScale.Fit,
                contentDescription = "logo",
                modifier = Modifier.fillMaxHeight()
            )
            GigaGuideMobileTheme {
                Button(
                    onClick = { Log.d("das", "btn") },
                    contentPadding = PaddingValues(6.dp),
                    modifier = Modifier
                        .defaultMinSize(minWidth = 1.dp, minHeight = 1.dp)
                        .fillMaxHeight()
                        .aspectRatio(1f),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.secondary
                    )
                ) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "search",
                        modifier = Modifier.fillMaxSize()
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
                style = MaterialTheme.typography.headlineSmall
            )
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.chevron_right),
                modifier = Modifier.size(40.dp),
                contentDescription = "right"
            )
        }

        var sights: List<SightTourThumbnail> = listOf(
            SightTourThumbnail("Sight1", "dasads", 4.8f, 5.5f),
            SightTourThumbnail("sight2", "dasads", 4.8f, 5.5f),
            SightTourThumbnail("sight2", "dasads", 4.8f, 5.5f)
        )
        Row(modifier = Modifier.horizontalScroll(rememberScrollState()).padding(vertical = 20.dp)) {
            for (sight in sights) {
                SightTourThumbnailBox(sight)
            }
        }


        Row(
            modifier = Modifier.padding(start = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                stringResource(R.string.home_screen_popular),
                style = MaterialTheme.typography.headlineSmall
            )
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.chevron_right),
                modifier = Modifier.size(40.dp),
                contentDescription = "right"
            )
        }

        Row(modifier = Modifier.horizontalScroll(rememberScrollState()).padding(vertical = 20.dp)) {
            for (sight in sights) {
                SightTourThumbnailBox(sight)
            }
        }

    }

}

@Composable
fun SightTourThumbnailBox(value: SightTourThumbnail) {

    Spacer(Modifier.width(20.dp))


    val cornerSize = 10.dp
    GigaGuideMobileTheme {
        Column(
            modifier = Modifier
                .width(275.dp)
                .shadow(elevation = 15.dp, shape = RoundedCornerShape(cornerSize))
                .border(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.secondary,
                    shape = RoundedCornerShape(cornerSize)
                )
                .background(color = MaterialTheme.colorScheme.background)


        ) {
            Image(
                painter = painterResource(R.drawable.jonkler),
                modifier = Modifier
                    .aspectRatio(300f / 135)
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop,
                contentDescription = "sight thumbnail image"
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    value.name,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(paddingValues = PaddingValues(start = 15.dp))
                )
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(PaddingValues(top = 4.dp, end = 4.dp, bottom = 2.dp)),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(value.rating.toString(), style = MaterialTheme.typography.titleSmall)
                        Icon(
                            imageVector = Icons.Filled.Star,
                            modifier = Modifier.size(20.dp),
                            contentDescription = "star icon"
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
                            style = MaterialTheme.typography.titleSmall
                        )
                        Icon(
                            imageVector = Icons.Outlined.Place,
                            modifier = Modifier.size(20.dp),
                            contentDescription = "proximity icon"
                        )
                    }
                }
            }
        }
    }

}

sealed class NavRoutes(val route: String) {
    object Home : NavRoutes("home")
    object Contacts : NavRoutes("contacts")
    object About : NavRoutes("about")
}