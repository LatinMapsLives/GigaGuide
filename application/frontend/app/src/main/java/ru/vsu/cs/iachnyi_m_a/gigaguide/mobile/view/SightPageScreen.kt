package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.R
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.viewmodel.SightPageScreenViewModel

@Composable
fun SightPageScreen(
    modifier: Modifier = Modifier,
    sightId: Long,
    sightPageScreenViewModel: SightPageScreenViewModel
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        var aspectRation: Float = 410f / 290
        Box(modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(aspectRation)) {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(aspectRation),
                painter = painterResource(R.drawable.jonkler),
                contentDescription = "Tour image",
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.Bottom) {
                
            }
        }
    }
}