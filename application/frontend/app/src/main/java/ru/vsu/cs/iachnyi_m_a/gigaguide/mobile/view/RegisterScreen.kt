package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.view


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.R
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.ui.theme.GigaGuideMobileTheme
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.ui.theme.Green
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.ui.theme.MediumGrey
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.ui.theme.Red
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.ui.theme.White
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.view.util.dropShadow
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.viewmodel.RegisterScreenViewModel

@Composable
fun RegisterScreen(
    registerScreenViewModel: RegisterScreenViewModel = hiltViewModel<RegisterScreenViewModel>(),
    navController: NavController
) {
    GigaGuideMobileTheme {
        Box(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.background)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Button(
                onClick = { navController.popBackStack() },
                contentPadding = PaddingValues(6.dp),
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(20.dp)
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
                    contentDescription = "search",
                    modifier = Modifier.fillMaxSize()
                )
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    imageVector = ImageVector.vectorResource(if (isSystemInDarkTheme()) R.drawable.logo_dark else R.drawable.logo),
                    contentDescription = "logo"
                )

                Text(
                    stringResource(R.string.register_screen_header),
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(20.dp),
                    color = MaterialTheme.colorScheme.onBackground
                )
                LoginRegisterTextField(
                    modifier = Modifier.dropShadow(
                        offsetY = 0.dp,
                        offsetX = 0.dp,
                        blur = 16.dp,
                        color = MaterialTheme.colorScheme.onBackground.copy(0.25f)
                    ),
                    value = registerScreenViewModel.userNameInput.value,
                    hint = stringResource(R.string.register_screen_hint_username),
                    onValueChange = {
                        registerScreenViewModel.registerScreenError.value =
                            RegisterScreenError.NONE
                        registerScreenViewModel.userNameInput.value = it
                    }
                )
                LoginRegisterTextField(
                    modifier = Modifier
                        .padding(25.dp)
                        .dropShadow(
                            offsetY = 0.dp,
                            offsetX = 0.dp,
                            blur = 16.dp,
                            color = MaterialTheme.colorScheme.onBackground.copy(0.25f)
                        ),
                    value = registerScreenViewModel.emailInput.value,
                    hint = stringResource(R.string.register_screen_hint_email),
                    onValueChange = {
                        registerScreenViewModel.registerScreenError.value =
                            RegisterScreenError.NONE
                        registerScreenViewModel.emailInput.value = it
                    }
                )
                LoginRegisterTextField(
                    modifier = Modifier.dropShadow(
                        offsetY = 0.dp,
                        offsetX = 0.dp,
                        blur = 16.dp,
                        color = MaterialTheme.colorScheme.onBackground.copy(0.25f)
                    ),
                    value = registerScreenViewModel.passwordInput.value,
                    hint = stringResource(R.string.register_screen_hint_password),
                    onValueChange = {
                        registerScreenViewModel.registerScreenError.value =
                            RegisterScreenError.NONE
                        registerScreenViewModel.passwordInput.value = it
                    }
                )
                LoginRegisterTextField(
                    modifier = Modifier
                        .padding(top = 25.dp)
                        .dropShadow(
                            offsetY = 0.dp,
                            offsetX = 0.dp,
                            blur = 16.dp,
                            color = MaterialTheme.colorScheme.onBackground.copy(0.25f)
                        ),
                    value = registerScreenViewModel.passwordConfirmInput.value,
                    hint = stringResource(R.string.register_screen_hint_confirm_password),
                    onValueChange = {
                        registerScreenViewModel.registerScreenError.value =
                            RegisterScreenError.NONE
                        registerScreenViewModel.passwordConfirmInput.value = it
                    }
                )
                var text = ""
                var error: RegisterScreenError = registerScreenViewModel.registerScreenError.value
                if (error == RegisterScreenError.FIELDS_EMPTY) {
                    text = stringResource(R.string.register_screen_error_empty_fields)
                } else if (error == RegisterScreenError.PASSWORD_MISMATCH) {
                    text = stringResource(R.string.register_screen_error_password_mismatch)
                } else if(error == RegisterScreenError.EMAIL_WRONG_FORMAT){
                    text = stringResource(R.string.register_screen_error_email_wrong_format)
                } else if (error == RegisterScreenError.REGISTER_FAILED) {
                    text = stringResource(R.string.register_screen_error_register_failed)
                } else if (registerScreenViewModel.registerSuccess.value) {
                    text = stringResource(R.string.register_screen_success)
                }
                Text(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth(),
                    text = text,
                    textAlign = TextAlign.Center,
                    color = if (registerScreenViewModel.registerSuccess.value) Green else Red,
                    style = MaterialTheme.typography.titleMedium
                )
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary,
                        contentColor = White,
                        disabledContainerColor = MediumGrey
                    ),
                    onClick = { registerScreenViewModel.registerUser() },
                    enabled = registerScreenViewModel.registerButtonActive.value
                ) {
                    Text(
                        text = stringResource(R.string.register_screen_button_text),
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.padding(vertical = 5.dp, horizontal = 50.dp)
                    )
                }
            }
        }
    }
}

enum class RegisterScreenError {
    FIELDS_EMPTY,
    PASSWORD_MISMATCH,
    EMAIL_WRONG_FORMAT,
    REGISTER_FAILED,
    NONE
}