package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.R
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.ui.theme.GigaGuideMobileTheme
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.ui.theme.Invisible
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.ui.theme.MediumBlue
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.ui.theme.White
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.view.util.dropShadow
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.viewmodel.LoginViewModel
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.viewmodel.NavigationViewModel
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.viewmodel.ScreenName

@Preview
@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel = LoginViewModel(),
    navigationViewModel: NavigationViewModel = NavigationViewModel()
) {
    GigaGuideMobileTheme {
        Box(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.background)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Button(
                onClick = { navigationViewModel.currentScreen.value = ScreenName.SETTINGS },
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
                    "Войти в аккаунт",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(20.dp),
                    color = MaterialTheme.colorScheme.onBackground
                )
                TextField(
                    value = loginViewModel.emailInput.value,
                    onValueChange = { loginViewModel.emailInput.value = it },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.primary,
                        unfocusedContainerColor = MaterialTheme.colorScheme.primary,
                        focusedIndicatorColor = Invisible,
                        unfocusedIndicatorColor = Invisible,
                        cursorColor = MaterialTheme.colorScheme.onPrimaryContainer
                    ),
                    shape = RoundedCornerShape(10.dp),
                    placeholder = {
                        Text(
                            "Электронная почта",
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    },
                    modifier = Modifier.dropShadow(
                        offsetY = 0.dp,
                        offsetX = 0.dp,
                        blur = 16.dp,
                        color = MaterialTheme.colorScheme.onBackground.copy(0.25f)
                    )
                )
                TextField(
                    value = loginViewModel.passwordInput.value,
                    onValueChange = { loginViewModel.passwordInput.value = it },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.primary,
                        unfocusedContainerColor = MaterialTheme.colorScheme.primary,
                        focusedIndicatorColor = Invisible,
                        unfocusedIndicatorColor = Invisible,
                        cursorColor = MaterialTheme.colorScheme.onPrimaryContainer
                    ),
                    shape = RoundedCornerShape(10.dp),
                    placeholder = {
                        Text("Пароль", color = MaterialTheme.colorScheme.onPrimaryContainer)
                    },
                    modifier = Modifier
                        .padding(25.dp)
                        .dropShadow(
                            offsetY = 0.dp,
                            offsetX = 0.dp,
                            blur = 16.dp,
                            color = MaterialTheme.colorScheme.onBackground.copy(0.25f)
                        )
                )
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary,
                        contentColor = White
                    ),
                    onClick = {}
                ) {
                    Text(
                        text = "Войти",
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.padding(vertical = 5.dp, horizontal = 80.dp)
                    )
                }
                Row(modifier = Modifier.padding(top = 20.dp)) {
                    Text("Нет аккаунта? ", color = MaterialTheme.colorScheme.onBackground)
                    Text("Зарегистрироваться", color = MediumBlue)
                }
            }
        }
    }
    //TODO: Add strings to resources
}
