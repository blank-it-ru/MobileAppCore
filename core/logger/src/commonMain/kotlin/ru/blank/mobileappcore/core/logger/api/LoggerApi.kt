package ru.blank.mobileappcore.core.logger.api

interface LoggerApi {
    fun e(tag: String?, message: String, throwable: Throwable? = null)
    fun d(tag: String?, message: String)
    fun i(tag: String?, message: String)
}
