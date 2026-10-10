package ru.blank.mobileappcore.network.provider.core

/**
 * Token storage
 *
 */
interface TokenStorage {
    var accessToken: String?
    var refreshToken: String?
    fun clear()
}
