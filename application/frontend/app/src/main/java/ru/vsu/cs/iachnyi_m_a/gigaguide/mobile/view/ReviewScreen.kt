package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.R
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.review.Review
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.navigation.LoginScreenObject
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.ui.theme.Black
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.ui.theme.GigaGuideMobileTheme
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.ui.theme.Invisible
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.ui.theme.LightGrey
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.ui.theme.MediumBlue
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.ui.theme.MediumGrey
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.ui.theme.Red
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.ui.theme.White
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.ui.theme.Yellow
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.view.util.dropShadow
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.viewmodel.ReviewScreenViewModel
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.util.Date
import java.util.Locale

@Composable
fun ReviewScreen(
    navController: NavController = rememberNavController(),
    objectId: Long,
    isTour: Boolean,
    reviewScreenViewModel: ReviewScreenViewModel = hiltViewModel<ReviewScreenViewModel>()
) {

    LaunchedEffect(Unit) {
        reviewScreenViewModel.isTour = isTour
        reviewScreenViewModel.objectId = objectId.toInt()
        reviewScreenViewModel.loadReviews()
    }

    GigaGuideMobileTheme {
        Column(
            modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = { navController.popBackStack() },
                    contentPadding = PaddingValues(6.dp),
                    modifier = Modifier
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
                        contentDescription = "chevron_left",
                        modifier = Modifier.fillMaxSize()
                    )
                }
                Text(
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.headlineSmall,
                    text = stringResource(R.string.review_screen_screen_header),
                    modifier = Modifier.padding(start = 20.dp)
                )
            }

            if (!reviewScreenViewModel.authorized) {
                Text(
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 20.dp),
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.headlineSmall,
                    text = stringResource(R.string.review_screen_login_to_leave_review)
                )
                Button(

                    modifier = Modifier
                        .padding(vertical = 20.dp)
                        .dropShadow(
                            shape = CircleShape,
                            offsetX = 0.dp,
                            offsetY = 0.dp,
                            blur = 16.dp,
                            color = MediumBlue
                        ),
                    colors = ButtonDefaults.buttonColors(
                        disabledContainerColor = MediumGrey,
                        containerColor = MaterialTheme.colorScheme.secondary,
                        contentColor = White
                    ),
                    onClick = { navController.navigate(LoginScreenObject) },
                ) {
                    Text(
                        text = stringResource(R.string.login_screen_login_button_text),
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier
                            .padding(vertical = 5.dp, horizontal = 80.dp)
                    )
                }
            } else if (!reviewScreenViewModel.loadingComments && reviewScreenViewModel.myReview == null) {
                AnimatedVisibility(visible = !reviewScreenViewModel.editorIsOpen) {
                    Button(
                        modifier = Modifier
                            .padding(bottom = 20.dp)
                            .dropShadow(
                                shape = CircleShape,
                                offsetX = 0.dp,
                                offsetY = 0.dp,
                                blur = 16.dp,
                                color = MediumBlue
                            ),
                        colors = ButtonDefaults.buttonColors(
                            disabledContainerColor = MediumGrey,
                            containerColor = MaterialTheme.colorScheme.secondary,
                            contentColor = White
                        ),
                        onClick = { reviewScreenViewModel.editorIsOpen = true },
                    ) {
                        Text(
                            text = stringResource(R.string.review_screen_leave_comment),
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier
                                .padding(vertical = 5.dp, horizontal = 40.dp)
                        )
                    }
                }
                AnimatedVisibility(visible = reviewScreenViewModel.editorIsOpen) {
                    ReviewEditor(
                        text = reviewScreenViewModel.newCommentText,
                        rating = reviewScreenViewModel.newCommentRating,
                        textChangeCallback = {
                            reviewScreenViewModel.emptyCommentError = false
                            reviewScreenViewModel.newCommentText = it
                        },
                        postCallback = { reviewScreenViewModel.postReview() },
                        closeEditorCallback = { reviewScreenViewModel.editorIsOpen = false },
                        ratingChangeCallback = { reviewScreenViewModel.newCommentRating = it },
                        postButtonActive = !reviewScreenViewModel.sendingComment
                    )
                }
                AnimatedVisibility(visible = reviewScreenViewModel.emptyCommentError) {
                    Text(
                        color = Red,
                        text = stringResource(R.string.review_screen_empty_comment_error),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Absolute.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = null,
                        tint = Yellow,
                        modifier = Modifier.size(45.dp)
                    )
                    Text(
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.headlineSmall,
                        text = "4.8",
                        modifier = Modifier.padding(start = 10.dp)
                    )
                }
                Text(
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.headlineSmall,
                    text = "16 оценок"
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 10.dp)
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.settings2_svgrepo_com),
                    modifier = Modifier.size(20.dp),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    color = MaterialTheme.colorScheme.onBackground,
                    text = "Новые и полезные",
                    modifier = Modifier.padding(start = 10.dp),
                    style = MaterialTheme.typography.labelLarge
                )
            }
            GradientSeparator(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 35.dp, end = 35.dp, top = 10.dp)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {
                if (!reviewScreenViewModel.loadingComments) {
                    if (reviewScreenViewModel.otherReviews.isEmpty() && reviewScreenViewModel.myReview == null) {
                        Text(
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp),
                            color = MaterialTheme.colorScheme.onBackground,
                            style = MaterialTheme.typography.titleLarge,
                            text = stringResource(R.string.review_screen_be_the_first)
                        )
                    } else {
                        if (reviewScreenViewModel.myReview != null) {
                            MyReviewBox(
                                review = reviewScreenViewModel.myReview!!,
                                deleteCallback = { reviewScreenViewModel.deleteReview() })
                            GradientSeparator(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 35.dp)
                            )
                        }
                        for (review in reviewScreenViewModel.otherReviews) {
                            ReviewBox(review)
                            GradientSeparator(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 35.dp)
                            )
                        }
                    }

                }

            }

        }
    }

}

@Composable
fun ReviewEditor(
    modifier: Modifier = Modifier,
    text: String,
    rating: Int,
    textChangeCallback: (String) -> Unit,
    ratingChangeCallback: (Int) -> Unit,
    postCallback: () -> Unit,
    closeEditorCallback: () -> Unit,
    postButtonActive: Boolean
) {
    Column {
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            for (i in 1..5) {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = null,
                    modifier = Modifier
                        .clickable(onClick = { ratingChangeCallback.invoke(i) })
                        .size(50.dp),
                    tint = if (rating >= i) Yellow else MediumGrey
                )
            }
        }
        TextField(
            enabled = postButtonActive,
            value = text,
            onValueChange = textChangeCallback,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.primary,
                unfocusedContainerColor = MaterialTheme.colorScheme.primary,
                focusedIndicatorColor = Invisible,
                unfocusedIndicatorColor = Invisible,
                cursorColor = MaterialTheme.colorScheme.onPrimaryContainer
            ),
            textStyle = MaterialTheme.typography.titleSmall,
            placeholder = {
                Text(
                    text = stringResource(R.string.review_screen_tell_your_impressions),
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    style = MaterialTheme.typography.titleSmall
                )
            },
            singleLine = false,
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 20.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(color = Invisible),
        )
        Row(horizontalArrangement = Arrangement.SpaceAround, modifier = Modifier.fillMaxWidth()) {
            Button(
                colors = ButtonDefaults.buttonColors(
                    disabledContainerColor = MediumGrey,
                    containerColor = MaterialTheme.colorScheme.secondary,
                    contentColor = White
                ),
                onClick = postCallback,
                enabled = postButtonActive
            ) {
                Text(
                    text = stringResource(R.string.review_screen_confirm_leave_comment),
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier
                        .padding(5.dp)
                )
            }
            Button(
                colors = ButtonDefaults.buttonColors(
                    disabledContainerColor = MediumGrey,
                    containerColor = MaterialTheme.colorScheme.secondary,
                    contentColor = White
                ),
                onClick = closeEditorCallback,
            ) {
                Text(
                    text = stringResource(R.string.review_screen_cancel_leave_comment),
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier
                        .padding(5.dp)
                )
            }
        }
    }
}

@Composable
fun ReviewBox(
    review: Review
) {
    GigaGuideMobileTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {

                    Icon(
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                            .background(color = LightGrey)
                            .padding(5.dp),
                        imageVector = ImageVector.vectorResource(R.drawable.person_outline),
                        tint = Black,
                        contentDescription = "user icon"
                    )

                    Text(
                        modifier = Modifier.padding(start = 10.dp),
                        text = review.userName,
                        style = MaterialTheme.typography.titleLarge
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = review.rating.toString(), style = MaterialTheme.typography.titleLarge
                    )
                    Icon(
                        modifier = Modifier.size(35.dp),
                        contentDescription = null,
                        imageVector = Icons.Filled.Star,
                        tint = Yellow
                    )
                }
            }
            Text(
                modifier = Modifier.padding(vertical = 10.dp),
                text = review.text,
                style = MaterialTheme.typography.bodyMedium
            )
            Row {
                Text(
                    color = MediumGrey,
                    style = MaterialTheme.typography.bodyMedium,
                    text = review.date.format(DateTimeFormatter.ofPattern("d MMMM yyyy"))/*SimpleDateFormat(
                        "yyyy-mm-dd", Locale.ROOT
                    ).format*/
                )
            }
        }
    }
}

@Composable
fun MyReviewBox(
    review: Review, deleteCallback: () -> Unit = {}
) {
    var optionsOpen by remember { mutableStateOf(false) }
    GigaGuideMobileTheme {
        Box(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .background(color = MaterialTheme.colorScheme.tertiary)
                    .fillMaxWidth()
                    .padding(15.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {

                        Icon(
                            modifier = Modifier
                                .size(50.dp)
                                .clip(CircleShape)
                                .background(color = LightGrey)
                                .padding(5.dp),
                            imageVector = ImageVector.vectorResource(R.drawable.person_outline),
                            tint = Black,
                            contentDescription = "user icon"
                        )

                        Text(
                            modifier = Modifier.padding(start = 10.dp),
                            text = review.userName,
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = review.rating.toString(),
                            style = MaterialTheme.typography.titleLarge
                        )
                        Icon(
                            modifier = Modifier.size(35.dp),
                            contentDescription = null,
                            imageVector = Icons.Filled.Star,
                            tint = Yellow
                        )
                    }
                }
                Text(
                    modifier = Modifier.padding(vertical = 10.dp),
                    text = review.text,
                    style = MaterialTheme.typography.bodyMedium
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        color = MediumGrey,
                        style = MaterialTheme.typography.bodyMedium,
                        text = review.date.toString()/*SimpleDateFormat(
                            "d MMMM yyyy", Locale("ru", "RU")
                        ).format(review.date)*/
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.pin),
                            tint = MediumGrey,
                            modifier = Modifier.size(20.dp),
                            contentDescription = null
                        )
                        Text(
                            color = MediumGrey,
                            text = stringResource(R.string.review_screen_label_your_review),
                            modifier = Modifier.padding(horizontal = 7.dp),
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.three_dots_vertical),
                            modifier = Modifier
                                .clickable(onClick = { optionsOpen = true })
                                .size(25.dp),
                            tint = Black,
                            contentDescription = null
                        )
                    }
                }
            }
            AnimatedVisibility(
                visible = optionsOpen, modifier = Modifier.align(Alignment.BottomEnd)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
                        .clip(
                            RoundedCornerShape(
                                topStart = 10.dp, topEnd = 10.dp, bottomStart = 10.dp
                            )
                        )
                        .background(
                            MaterialTheme.colorScheme.background
                        )
                ) {
                    Text(
                        modifier = Modifier.clickable(onClick = deleteCallback).padding(15.dp),
                        text = stringResource(R.string.review_screen_button_delete_comment),
                        color = Red,
                        style = MaterialTheme.typography.titleMedium
                    )
                    GradientSeparator(
                        modifier = Modifier
                            .padding(horizontal = 15.dp)
                            .width(100.dp)
                    )
                    Text(
                        modifier = Modifier.clickable(onClick = { optionsOpen = false }).padding(15.dp),
                        text = stringResource(R.string.review_screen_button_cancel_deletion),
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }


        }

    }
}