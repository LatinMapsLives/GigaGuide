package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.R
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.ui.theme.Invisible
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.ui.theme.MediumGrey
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.ui.theme.Red
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.ui.theme.White
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.viewmodel.ProfileScreenViewModel

@Composable
fun ProfileScreen(
    navController: NavController,
    profileScreenViewModel: ProfileScreenViewModel = hiltViewModel<ProfileScreenViewModel>()
) {

    profileScreenViewModel.emailSuccess = stringResource(R.string.profile_screen_email_success)
    profileScreenViewModel.passwordSuccess = stringResource(R.string.profile_screen_password_success)
    profileScreenViewModel.usernameSuccess = stringResource(R.string.profile_screen_username_success)

    LaunchedEffect(Unit) {
        profileScreenViewModel.loadUserData()
    }
    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.background)
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(bottom = 10.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RoundedCornerSquareButton(
                modifier = Modifier.height(50.dp),
                imageVector = ImageVector.vectorResource(R.drawable.chevron_left),
                onClick = { navController.popBackStack() }
            )
            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 10.dp),
                text = stringResource(R.string.profile_screen_header),
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.headlineSmall
            )
        }

        GradientSeparator(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 35.dp)
        )

        Text(
            modifier = Modifier.padding(vertical = 10.dp),
            text = stringResource(R.string.profile_screen_username),
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.titleLarge
        )

        Row(
            modifier = Modifier.padding(bottom = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (profileScreenViewModel.loading) {
                Spacer(
                    modifier = Modifier
                        .clip(RoundedCornerShape(5.dp))
                        .background(MaterialTheme.colorScheme.tertiary)
                        .height(20.dp)
                        .height(60.dp)
                )
            } else {
                Text(
                    text = profileScreenViewModel.currentUsername,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Bold
                )
            }

            EditUserDataButton(
                modifier = Modifier.padding(start = 10.dp),
                onClick = {
                    profileScreenViewModel.usernameEditorOpen =
                        !profileScreenViewModel.usernameEditorOpen
                },
                text = if (profileScreenViewModel.usernameEditorOpen)
                    stringResource(R.string.profile_screen_button_change_inactive)
                else stringResource(R.string.profile_screen_button_change_active),
                enabled = true
            )
        }

        AnimatedVisibility(visible = profileScreenViewModel.usernameEditorOpen) {
            Row(
                modifier = Modifier.padding(bottom = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                EditUserDataTextField(
                    modifier = Modifier
                        .height(50.dp)
                        .weight(1f),
                    value = profileScreenViewModel.newUsername,
                    onValueChange = { profileScreenViewModel.newUsername = it },
                    hint = stringResource(R.string.profile_screen_hint_new_name),
                    isPassword = false
                )
                EditUserDataButton(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 10.dp),
                    onClick = {
                        profileScreenViewModel.updateUsername()
                    },
                    enabled = !profileScreenViewModel.loading,
                    text = stringResource(R.string.profile_screen_button_confirm_change)
                )
            }
        }

        AnimatedVisibility( visible = profileScreenViewModel.usernameChangeError != R.string.empty) {
            Text(modifier = Modifier.padding(bottom = 10.dp),color = Red, text = stringResource(profileScreenViewModel.usernameChangeError))
        }

        GradientSeparator(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 35.dp)
        )

        Text(
            modifier = Modifier.padding(vertical = 10.dp),
            text = stringResource(R.string.profile_screen_email),
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.titleLarge
        )

        Row(
            modifier = Modifier.padding(bottom = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (profileScreenViewModel.loading) {
                Spacer(
                    modifier = Modifier
                        .clip(RoundedCornerShape(5.dp))
                        .background(MaterialTheme.colorScheme.tertiary)
                        .height(20.dp)
                        .height(60.dp)
                )
            } else {
                Text(
                    text = profileScreenViewModel.currentEmail,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Bold
                )
            }

            EditUserDataButton(
                modifier = Modifier.padding(start = 10.dp),
                onClick = {
                    profileScreenViewModel.emailEditorOpen =
                        !profileScreenViewModel.emailEditorOpen
                },
                text = if (profileScreenViewModel.emailEditorOpen)
                    stringResource(R.string.profile_screen_button_change_inactive)
                else stringResource(R.string.profile_screen_button_change_active),
                enabled = true
            )
        }

        AnimatedVisibility(visible = profileScreenViewModel.emailEditorOpen) {
            Row(
                modifier = Modifier.padding(bottom = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                EditUserDataTextField(
                    modifier = Modifier
                        .height(50.dp)
                        .weight(1f),
                    value = profileScreenViewModel.newEmail,
                    onValueChange = { profileScreenViewModel.newEmail = it },
                    hint = stringResource(R.string.profile_screen_hint_new_email),
                    isPassword = false
                )
                EditUserDataButton(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 10.dp),
                    onClick = {
                        profileScreenViewModel.updateEmail()
                    },
                    enabled = !profileScreenViewModel.loading,
                    text = stringResource(R.string.profile_screen_button_confirm_change)
                )
            }
        }

        AnimatedVisibility( visible = profileScreenViewModel.emailChangeError != R.string.empty) {
            Text(modifier = Modifier.padding(bottom = 10.dp),color = Red, text = stringResource(profileScreenViewModel.emailChangeError))
        }

        GradientSeparator(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 35.dp)
        )

        Text(
            modifier = Modifier.padding(vertical = 10.dp),
            text = stringResource(R.string.profile_screen_password),
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.titleLarge
        )

        Row(
            modifier = Modifier.padding(bottom = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            EditUserDataButton(
                onClick = {
                    profileScreenViewModel.passwordEditorOpen =
                        !profileScreenViewModel.passwordEditorOpen
                },
                text = if (profileScreenViewModel.passwordEditorOpen)
                    stringResource(R.string.profile_screen_button_change_inactive)
                else stringResource(R.string.profile_screen_button_change_active),
                enabled = true
            )
        }

        AnimatedVisibility(visible = profileScreenViewModel.passwordEditorOpen) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp)
            ) {
                EditUserDataTextField(
                    modifier = Modifier
                        .padding(bottom = 10.dp)
                        .height(50.dp)
                        .fillMaxWidth(0.5f),
                    value = profileScreenViewModel.oldPassword,
                    onValueChange = { profileScreenViewModel.oldPassword = it },
                    hint = stringResource(R.string.profile_screen_current_password),
                    isPassword = true
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    EditUserDataTextField(
                        modifier = Modifier
                            .height(50.dp)
                            .weight(1f),
                        value = profileScreenViewModel.newPassword,
                        onValueChange = { profileScreenViewModel.newPassword = it },
                        hint = stringResource(R.string.profile_screen_new_password),
                        isPassword = true
                    )
                    EditUserDataButton(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 10.dp),
                        onClick = {
                            profileScreenViewModel.updatePassword()
                        },
                        enabled = !profileScreenViewModel.loading,
                        text = stringResource(R.string.profile_screen_button_confirm_change)
                    )
                }

            }
        }

        AnimatedVisibility( visible = profileScreenViewModel.passwordChangeError != R.string.empty) {
            Text(modifier = Modifier.padding(bottom = 10.dp),color = Red, text = stringResource(profileScreenViewModel.passwordChangeError))
        }

        GradientSeparator(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 35.dp)
        )

        Text(
            text = stringResource(R.string.profile_screen_log_out),
            style = MaterialTheme.typography.titleMedium,
            color = Red,
            modifier = Modifier
                .padding(top = 10.dp)
                .clickable(onClick = {
                    profileScreenViewModel.logout {
                        navController.popBackStack()
                    }
                })
        )
    }

}

@Composable
fun EditUserDataTextField(
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
        textStyle = MaterialTheme.typography.titleSmall,
        placeholder = {
            Text(
                hint,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                style = MaterialTheme.typography.titleSmall
            )
        },
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(color = Invisible),
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None
    )
}

@Composable
fun EditUserDataButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enabled: Boolean = true,
    text: String
) {
    Button(
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            disabledContainerColor = MediumGrey,
            containerColor = MaterialTheme.colorScheme.secondary,
            contentColor = White
        ),
        onClick = onClick,
        enabled = enabled
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium,
        )
    }
}