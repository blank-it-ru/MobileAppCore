package ru.blank.mobileappcore.network.provider.core

/**
 * Модель пары токенов аутентификации.
 *
 * @property accessToken токен доступа для авторизации API запросов (короткий срок жизни)
 * @property refreshToken токен обновления для получения нового access токена (длительный срок жизни)
 */
data class TokenPairModel(
    val accessToken: String,
    val refreshToken: String
)
