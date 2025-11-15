package com.example.dndredactor.data

object AppConstants {
    const val BASE_URL = "https://api.example.com/v1/"
    const val TIMEOUT_SECONDS = 5L
    val fieldFillingError = "Все поля должны быть заполнены."
    val unableToLogin = "Не удалось войти. Попробуйте позже."
    val emailError = "Некорректная форма почты."
    val failedToRegister = "Не удалось зарегистрирвоваться. Попробуйте позже."
    val passwordError =
        "Длина пароля должна быть минимум 8 символов. Пароль должен содержать хотя бы одну цифру, заглавную букву и специальный символ."
    val passwordMatchError = "Пароли должны совпадать."
    val characterLoadError: String = "Не удалось загрузить персонажей."
    val characterDeleteError: String = "Не удалось удалить персонажа."
    val loadError = "Ошибка загрузки"
}