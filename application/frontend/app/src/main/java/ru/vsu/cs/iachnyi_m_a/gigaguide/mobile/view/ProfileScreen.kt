package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.R
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.ui.theme.MediumGrey
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.ui.theme.White
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.viewmodel.ProfileScreenViewModel

@Composable
fun ProfileScreen(
    navController: NavController,
    profileScreenViewModel: ProfileScreenViewModel = hiltViewModel<ProfileScreenViewModel>()
) {
    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.background)
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            RoundedCornerSquareButton(
                modifier = Modifier.size(40.dp),
                imageVector = ImageVector.vectorResource(R.drawable.chevron_left),
                onClick = { navController.popBackStack() }
            )
            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 10.dp),
                text = "Информация профиля",
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.headlineLarge
            )
        }
        Text(
            modifier = Modifier.padding(vertical = 10.dp),
            text = "Имя пользователя",
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.headlineSmall
        )

        Row (verticalAlignment = Alignment.CenterVertically) {
            Text(text = profileScreenViewModel.currentUsername)
            Button(
                modifier = Modifier.padding(start = 10.dp),
                colors = ButtonDefaults.buttonColors(
                    disabledContainerColor = MediumGrey,
                    containerColor = MaterialTheme.colorScheme.secondary,
                    contentColor = White
                ),
                onClick = { profileScreenViewModel.usernameEditorOpen = !profileScreenViewModel.usernameEditorOpen },
                enabled = profileScreenViewModel.currentUsername.isNotEmpty()
            ) {
                Text(
                    text = "Изменить",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(vertical = 5.dp, horizontal = 20.dp)
                )
            }
        }

        Text(
            modifier = Modifier.padding(vertical = 10.dp),
            text = "Электронная почта",
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.headlineSmall
        )

        Row (verticalAlignment = Alignment.CenterVertically) {
            Text(text = profileScreenViewModel.currentEmail)
            Button(
                modifier = Modifier.padding(start = 10.dp),
                colors = ButtonDefaults.buttonColors(
                    disabledContainerColor = MediumGrey,
                    containerColor = MaterialTheme.colorScheme.secondary,
                    contentColor = White
                ),
                onClick = { profileScreenViewModel.emailEditorOpen = !profileScreenViewModel.emailEditorOpen },
                enabled = profileScreenViewModel.currentEmail.isNotEmpty()
            ) {
                Text(
                    text = "Изменить",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(vertical = 5.dp, horizontal = 20.dp)
                )
            }
        }

        Text(
            modifier = Modifier.padding(vertical = 10.dp),
            text = "Пароль",
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.headlineSmall
        )

        Row (verticalAlignment = Alignment.CenterVertically) {
            Button(
                modifier = Modifier.padding(start = 10.dp),
                colors = ButtonDefaults.buttonColors(
                    disabledContainerColor = MediumGrey,
                    containerColor = MaterialTheme.colorScheme.secondary,
                    contentColor = White
                ),
                onClick = { profileScreenViewModel.passwordEditorOpen = !profileScreenViewModel.passwordEditorOpen },
                enabled = profileScreenViewModel.currentUsername.isNotEmpty()
            ) {
                Text(
                    text = "Изменить",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(vertical = 5.dp, horizontal = 20.dp)
                )
            }
        }
    }

}