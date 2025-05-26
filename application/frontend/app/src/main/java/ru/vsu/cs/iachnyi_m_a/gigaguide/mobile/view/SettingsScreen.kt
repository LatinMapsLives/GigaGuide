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
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.R
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.navigation.LoginScreenObject
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.navigation.ProfileScreenObject
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.ui.theme.GigaGuideMobileTheme
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.ui.theme.MediumBlue
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.ui.theme.White
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.util.LocaleManager
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.util.RememberLocale
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.util.ThemeSettings
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.view.util.dropShadow
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.viewmodel.SettingsScreenViewModel

@Composable
fun SettingsScreen(settingsScreenViewModel: SettingsScreenViewModel, navController: NavController) {
    LaunchedEffect(Unit) {
        settingsScreenViewModel.discoverJWT()
        settingsScreenViewModel.loadSettings()
    }

    RememberLocale(
        LocaleManager.currentLanguage
    ) { LocaleManager.recomposeFlag = !LocaleManager.recomposeFlag }

    key(LocaleManager.recomposeFlag) {
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
                            key(LocaleManager.recomposeFlag) {
                                Text(
                                    text = stringResource(R.string.setting_screen_login_button_text),
                                    style = MaterialTheme.typography.headlineSmall,
                                    modifier = Modifier.padding(vertical = 5.dp, horizontal = 60.dp)
                                )
                            }
                        }
                    }
                }

                var themeDialogOpen by remember { mutableStateOf(false) }
                var languageDialogOpen by remember { mutableStateOf(false) }

                when {
                    themeDialogOpen -> {
                        Dialog(onDismissRequest = { themeDialogOpen = false }) {
                            Column(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(20.dp))
                                    .background(color = MaterialTheme.colorScheme.background)
                                    .padding(20.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = stringResource(R.string.settings_screen_setting_name_theme),
                                    color = MaterialTheme.colorScheme.onBackground,
                                    style = MaterialTheme.typography.titleLarge
                                )
                                val radioOptions = listOf(
                                    ThemeSettings.AS_DEVICE, ThemeSettings.ALWAYS_LIGHT,
                                    ThemeSettings.ALWAYS_DARK
                                )
                                val radioLabels = listOf(
                                    stringResource(R.string.settings_screen_theme_as_device),
                                    stringResource(R.string.settings_screen_theme_always_light),
                                    stringResource(R.string.settings_screen_theme_always_dark)
                                )
                                Column(Modifier.selectableGroup()) {
                                    radioOptions.forEachIndexed { i, option ->
                                        Row(
                                            Modifier
                                                .fillMaxWidth()
                                                .selectable(
                                                    selected = (option == settingsScreenViewModel.themeSetting),
                                                    onClick = {
                                                        settingsScreenViewModel.themeSetting =
                                                            option
                                                        settingsScreenViewModel.updateThemeSettings()
                                                    },
                                                    role = Role.RadioButton
                                                )
                                                .padding(top = 10.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            RadioButton(
                                                selected = (settingsScreenViewModel.themeSetting == option),
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
                            }
                        }
                    }
                }

                when {
                    languageDialogOpen -> {
                        Dialog(onDismissRequest = { languageDialogOpen = false }) {
                            Column(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(20.dp))
                                    .background(color = MaterialTheme.colorScheme.background)
                                    .padding(20.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = stringResource(R.string.settings_screen_setting_name_language),
                                    color = MaterialTheme.colorScheme.onBackground,
                                    style = MaterialTheme.typography.titleLarge
                                )
                                val radioOptions = listOf(
                                    "ru", "en"
                                )
                                val radioLabels = listOf(
                                    "Русский",
                                    "English",
                                )
                                Column(Modifier.selectableGroup()) {
                                    radioOptions.forEachIndexed { i, option ->
                                        Row(
                                            Modifier
                                                .fillMaxWidth()
                                                .selectable(
                                                    selected = (option == settingsScreenViewModel.currentLanguage),
                                                    onClick = {
                                                        settingsScreenViewModel.currentLanguage =
                                                            option
                                                        settingsScreenViewModel.updateAppLanguage()
                                                    },
                                                    role = Role.RadioButton
                                                )
                                                .padding(top = 10.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            RadioButton(
                                                selected = (settingsScreenViewModel.currentLanguage == option),
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
                            }
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
                        name = stringResource(R.string.settings_screen_setting_name_language),
                        action = { languageDialogOpen = true }
                    ),
                    SettingsButtonContent(
                        icon = ImageVector.vectorResource(R.drawable.moon),
                        name = stringResource(R.string.settings_screen_setting_name_theme),
                        action = { themeDialogOpen = true }
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
                            .padding(15.dp),
                        action = btn.action
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
fun SettingsButton(icon: ImageVector, name: String, modifier: Modifier, action: () -> Unit) {
    val iconSize = 35.dp
    Row(
        modifier = modifier
            .clickable(onClick = action)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
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
    var action: () -> Unit
)
