package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.sight.SightTourThumbnail
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.ui.theme.GigaGuideMobileTheme
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.ui.theme.Invisible
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.view.util.dropShadow

@Composable
fun CustomTextField(
    modifier: Modifier,
    hint: String,
    value: String,
    onValueChange: (String) -> Unit,
    isPassword: Boolean
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.primary,
            unfocusedContainerColor = MaterialTheme.colorScheme.primary,
            focusedIndicatorColor = Invisible,
            unfocusedIndicatorColor = Invisible,
            cursorColor = MaterialTheme.colorScheme.onPrimaryContainer
        ),
        placeholder = {
            Text(hint, color = MaterialTheme.colorScheme.onPrimaryContainer)
        },
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(color = Invisible),
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None
    )
}

@Composable
fun SightTourThumbnailBox(modifier: Modifier, value: SightTourThumbnail) {

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

@Composable
fun LoadingThumbnailBox(modifier: Modifier) {
    var loadingColor: Color = MaterialTheme.colorScheme.tertiary
    val cornerSize = 10.dp
    val spacerCornerSize = 5.dp
    GigaGuideMobileTheme {

        Box(
            modifier = modifier
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
fun RoundedCornerSquareButton(modifier: Modifier, onClick: () -> Unit, imageVector: ImageVector, contentColor: Color = MaterialTheme.colorScheme.secondary, containerColor: Color = MaterialTheme.colorScheme.primary) {
    Button(
        onClick = onClick,
        contentPadding = PaddingValues(6.dp),
        modifier = modifier
            .aspectRatio(1f),
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = "search",
            modifier = Modifier.fillMaxSize()
        )
    }
}