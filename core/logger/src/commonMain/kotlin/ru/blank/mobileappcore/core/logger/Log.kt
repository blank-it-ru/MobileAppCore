package ru.blank.mobileappcore.core.logger

import ru.blank.mobileappcore.core.logger.api.LoggerApi

expect object Log : LoggerApi {
    override fun e(tag: String?, message: String, throwable: Throwable?)
    override fun d(tag: String?, message: String)
    override fun i(tag: String?, message: String)
}
