package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.R
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.Review
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.ui.theme.Black
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.ui.theme.GigaGuideMobileTheme
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.ui.theme.LightGrey
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.ui.theme.MediumGrey
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.ui.theme.Yellow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Preview
@Composable
fun ReviewScreen(navController: NavController = rememberNavController(), sightId: Long = 0) {
    var reviews = listOf(
        Review(
            "Markini",
            5,
            "Только что завершил тур с аудиогидом и остался в полном восторге! Очень удобный формат: можно двигаться в своём темпе, останавливаться у заинтересовавших объектов и не зависеть от группы. Информация подана чётко, увлекательно и без лишней воды.",
            date = Date(1212121212121L)
        ),
        Review(
            userName = "Турист228",
            text = "Недавно воспользовался аудиогидом во время экскурсии, и это был потрясающий опыт! Всё было организовано очень удобно: понятный интерфейс, отличное качество звука и интересный, живой рассказ. Особенно понравилось, что можно двигаться в своём темпе.",
            rating = 5,
            date = Date(1210621212121L)
        ),
        Review(
            userName = "Jonkler",
            text = "Понравился гибкий формат: шёл в своём темпе, слушал чёткие и лаконичные пояснения. Иногда не хватало чуть больше деталей, но в целом – отличный вариант для самостоятельной экскурсии.",
            rating = 4,
            date = Date(1210021212121L)
        )
    )
    GigaGuideMobileTheme {
        Column(modifier = Modifier.fillMaxSize()) {
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
                    style = MaterialTheme.typography.headlineSmall,
                    text = "Оценки и отзывы",
                    modifier = Modifier.padding(start = 20.dp)
                )
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
                        style = MaterialTheme.typography.headlineSmall,
                        text = "4.8",
                        modifier = Modifier.padding(start = 10.dp)
                    )
                }
                Text(style = MaterialTheme.typography.headlineSmall, text = "16 оценок")
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp)
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.settings2_svgrepo_com),
                    modifier = Modifier.size(20.dp),
                    contentDescription = null
                )
                Text(
                    text = "Новые и полезные",
                    modifier = Modifier.padding(start = 10.dp),
                    style = MaterialTheme.typography.labelLarge
                )
            }
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                for (review in reviews) {
                    GradientSeparator(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 35.dp)
                    )
                    ReviewBox(review)
                }
            }
            GradientSeparator(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 35.dp))
        }
    }

}

@Composable
fun ReviewBox(
    review: Review = Review(
        userName = "Турист228",
        text = "Недавно воспользовался аудиогидом во время экскурсии, и это был потрясающий опыт! Всё было организовано очень удобно: понятный интерфейс, отличное качество звука и интересный, живой рассказ. Особенно понравилось, что можно двигаться в своём темпе.",
        rating = 4,
        date = Date(1212121212121L)
    )
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
                style = MaterialTheme.typography.bodySmall
            )
            Row {
                Text(
                    color = MediumGrey,
                    style = MaterialTheme.typography.bodyMedium,
                    text = SimpleDateFormat(
                        "yyyy-mm-dd",
                        Locale.ROOT
                    ).format(review.date)
                )
            }
        }
    }
}