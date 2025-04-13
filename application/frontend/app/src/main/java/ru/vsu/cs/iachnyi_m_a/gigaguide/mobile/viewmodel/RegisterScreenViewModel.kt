package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class RegisterScreenViewModel: ViewModel() {
    var emailInput = mutableStateOf("")
    var userNameInput = mutableStateOf("")
    var passwordInput = mutableStateOf("")
    var passwordConfirmInput = mutableStateOf("")
}