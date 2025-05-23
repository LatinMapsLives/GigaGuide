package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
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
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.navigation.RegisterScreenObject
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.ui.theme.GigaGuideMobileTheme
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.ui.theme.Green
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.ui.theme.MediumBlue
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.ui.theme.MediumGrey
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.ui.theme.Red
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.ui.theme.White
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.util.Pancake
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.view.util.dropShadow
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.viewmodel.LoginScreenViewModel

enum class LoginScreenError{
    FIELDS_EMPTY,
    LOGIN_ERROR,
    NONE
}

@Composable
fun LoginScreen(
    loginScreenViewModel: LoginScreenViewModel = hiltViewModel<LoginScreenViewModel>(),
    navController: NavController
) {
    var loginSuccessString = stringResource(R.string.success_login_success)
    loginScreenViewModel.popNavigationBackStackCallback = {
        Pancake.success(loginSuccessString)
        navController.popBackStack()}
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
                    contentDescription = "chevron_left",
                    modifier = Modifier.fillMaxSize()
                )
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    imageVector = ImageVector.vectorResource(if (isSystemInDarkTheme()) R.drawable.logo_dark else R.drawable.logo),
                    contentDescription = "logo"
                )

                Text(
                    stringResource(R.string.login_screen_log_in_header),
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
                    value = loginScreenViewModel.emailInput.value,
                    hint = stringResource(R.string.login_screen_email_hint),
                    onValueChange = { loginScreenViewModel.emailInput.value = it },
                    isPassword = false
                )
                LoginRegisterTextField(
                    modifier = Modifier
                        .padding(top=25.dp)
                        .dropShadow(
                            offsetY = 0.dp,
                            offsetX = 0.dp,
                            blur = 16.dp,
                            color = MaterialTheme.colorScheme.onBackground.copy(0.25f)
                        ),
                    value = loginScreenViewModel.passwordInput.value,
                    hint = stringResource(R.string.login_screen_password_hint),
                    onValueChange = { loginScreenViewModel.passwordInput.value = it },
                    isPassword = true
                )
                var text = ""
                var error: LoginScreenError = loginScreenViewModel.error.value
                if (error == LoginScreenError.FIELDS_EMPTY) {
                    text = stringResource(R.string.login_screen_error_fields_empty)
                } else if (error == LoginScreenError.LOGIN_ERROR) {
                    text = stringResource(R.string.login_screen_error_login_error)
                } else if (loginScreenViewModel.loginSuccess.value) {
                    text = stringResource(R.string.login_screen_success)
                }
                Text(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth(),
                    text = text,
                    textAlign = TextAlign.Center,
                    color = if (loginScreenViewModel.loginSuccess.value) Green else Red,
                    style = MaterialTheme.typography.titleMedium
                )
                Button(
                    colors = ButtonDefaults.buttonColors(
                        disabledContainerColor = MediumGrey,
                        containerColor = MaterialTheme.colorScheme.secondary,
                        contentColor = White
                    ),
                    onClick = { loginScreenViewModel.loginUser() },
                    enabled = loginScreenViewModel.loginButtonActive.value
                ) {
                    Text(
                        text = stringResource(R.string.login_screen_login_button_text),
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.padding(vertical = 5.dp, horizontal = 80.dp)
                    )
                }
                Row(modifier = Modifier.padding(top = 20.dp)) {
                    Text(stringResource(R.string.login_screen_no_account_question) + ' ', color = MaterialTheme.colorScheme.onBackground)
                    Text(stringResource(R.string.login_screen_register_link), color = MediumBlue, modifier = Modifier.clickable(onClick = {navController.navigate(
                        RegisterScreenObject)}))
                }
            }
        }
    }
}
