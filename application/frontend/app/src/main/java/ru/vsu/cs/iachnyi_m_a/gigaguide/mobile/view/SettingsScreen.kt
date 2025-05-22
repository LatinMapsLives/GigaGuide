package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.R
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.navigation.LoginScreenObject
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.navigation.ProfileScreenObject
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.ui.theme.GigaGuideMobileTheme
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.ui.theme.MediumBlue
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.ui.theme.White
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.view.util.dropShadow
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.viewmodel.SettingsScreenViewModel

@Composable
fun SettingsScreen(settingsScreenViewModel: SettingsScreenViewModel, navController: NavController) {
    LaunchedEffect(Unit) {
        settingsScreenViewModel.discoverJWT()
    }
    GigaGuideMobileTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (settingsScreenViewModel.loading.value) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .padding(20.dp)
                        .size(100.dp)
                )
            } else {
                if (settingsScreenViewModel.userData.value != null) {
                    Row(
                        modifier = Modifier
                            .clickable(onClick = { navController.navigate(ProfileScreenObject) })
                            .background(color = MaterialTheme.colorScheme.tertiary)
                            .padding(20.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .border(
                                    shape = CircleShape,
                                    width = 3.dp,
                                    color = MaterialTheme.colorScheme.secondary
                                )
                                .clip(CircleShape)
                                .background(color = MaterialTheme.colorScheme.primary)
                        ) {
                            Icon(
                                modifier = Modifier.fillMaxSize(),
                                imageVector = ImageVector.vectorResource(R.drawable.person_outline),
                                tint = MaterialTheme.colorScheme.secondary,
                                contentDescription = "user icon"
                            )
                        }
                        //Text("")
                        Text(
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onBackground,
                            text = "${stringResource(R.string.settings_screen_logged_in_as)} ${settingsScreenViewModel.userData.value!!.username}",
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 20.dp)
                        )
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.chevron_right),
                            contentDescription = "chevron right",
                            modifier = Modifier
                                .size(50.dp),
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                    GradientSeparator(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 35.dp)
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .padding(20.dp)
                            .size(80.dp)
                            .border(
                                shape = CircleShape,
                                width = 6.dp,
                                color = MaterialTheme.colorScheme.secondary
                            )
                            .clip(CircleShape)
                            .background(color = MaterialTheme.colorScheme.primary)
                    ) {
                        Icon(
                            modifier = Modifier.fillMaxSize(),
                            imageVector = ImageVector.vectorResource(R.drawable.person_outline),
                            tint = MaterialTheme.colorScheme.secondary,
                            contentDescription = "user icon"
                        )
                    }
                    Text(
                        modifier = Modifier.padding(horizontal = 20.dp),
                        style = MaterialTheme.typography.titleLarge,
                        text = stringResource(R.string.settings_screen_all_advantages_header),
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Button(
                        modifier = Modifier
                            .padding(vertical = 20.dp)
                            .dropShadow(
                                offsetX = 0.dp,
                                offsetY = 0.dp,
                                blur = 16.dp,
                                shape = RoundedCornerShape(20.dp),
                                color = MediumBlue
                            ),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondary,
                            contentColor = White
                        ),
                        onClick = { navController.navigate(LoginScreenObject) }
                    ) {
                        Text(
                            text = stringResource(R.string.setting_screen_login_button_text),
                            style = MaterialTheme.typography.headlineSmall,
                            modifier = Modifier.padding(vertical = 5.dp, horizontal = 60.dp)
                        )
                    }
                }
            }


            Text(
                text = stringResource(R.string.settings_screen_settings_header),
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth(),
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onBackground
            )
            var buttons = listOf<SettingsButtonContent>(
                SettingsButtonContent(
                    icon = ImageVector.vectorResource(R.drawable.language),
                    name = stringResource(R.string.settings_screen_setting_name_language)
                ),
                SettingsButtonContent(
                    icon = ImageVector.vectorResource(R.drawable.moon),
                    name = stringResource(R.string.settings_screen_setting_name_theme)
                )
            )
            for (btn in buttons) {
                GradientSeparator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 30.dp)
                )
                SettingsButton(
                    icon = btn.icon,
                    name = btn.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp)
                )
            }
            GradientSeparator(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp)
            )
        }
    }

}

@Composable
fun UserIcon(modifier: Modifier = Modifier) {
    GigaGuideMobileTheme {
        Box(
            modifier = modifier
                .size(60.dp)
                .border(
                    shape = CircleShape,
                    width = 6.dp,
                    color = MaterialTheme.colorScheme.secondary
                )
                .clip(CircleShape)
                .background(color = MaterialTheme.colorScheme.primary)
        ) {
            Icon(
                modifier = Modifier.fillMaxSize(),
                imageVector = ImageVector.vectorResource(R.drawable.person_outline),
                tint = MaterialTheme.colorScheme.secondary,
                contentDescription = "user icon"
            )
        }
    }

}

@Composable
fun GradientSeparator(modifier: Modifier) {
    Spacer(
        modifier = modifier
            .height(1.dp)
            .background(
                brush = Brush.horizontalGradient(
                    colorStops = arrayOf(
                        0.0f to MaterialTheme.colorScheme.onBackground.copy(0f),
                        0.5f to MaterialTheme.colorScheme.onBackground,
                        1f to MaterialTheme.colorScheme.onBackground.copy(0f)
                    )
                )
            )
    )
}

@Composable
fun SettingsButton(icon: ImageVector, name: String, modifier: Modifier) {
    val iconSize = 35.dp
    Row(modifier = modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = icon,
            contentDescription = "settings icon",
            modifier = Modifier.size(iconSize),
            tint = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = name,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 10.dp),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground

        )
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.chevron_right),
            contentDescription = "chevron right",
            modifier = Modifier.size(iconSize),
            tint = MaterialTheme.colorScheme.onBackground
        )
    }
}

private class SettingsButtonContent(
    var icon: ImageVector,
    var name: String,
)
