package ru.blank.mobileappcore.data

import com.russhwolf.settings.Settings
import ru.blank.mobileappcore.network.provider.core.TokenStorage

class TokenStorageImpl(
    private val settings: Settings,
) : TokenStorage {
    override var accessToken: String?
        get() = settings.getStringOrNull(ACCESS_TOKEN)
        set(value) {
            value?.let { settings.putString(ACCESS_TOKEN, it) } ?: settings.remove(ACCESS_TOKEN)
        }

    override var refreshToken: String?
        get() = settings.getStringOrNull(REFRESH_TOKEN)
        set(value) {
            value?.let { settings.putString(REFRESH_TOKEN, it) }
                ?: settings.remove(REFRESH_TOKEN)
        }

    override fun clear() {
        accessToken = null
        refreshToken = null
    }

    companion object {
        private const val ACCESS_TOKEN = "access_token"
        private const val REFRESH_TOKEN = "refresh_token"
    }
}
