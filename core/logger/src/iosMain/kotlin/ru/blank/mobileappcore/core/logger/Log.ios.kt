package ru.blank.mobileappcore.core.logger

import platform.Foundation.NSLog
import ru.blank.mobileappcore.core.logger.api.LoggerApi

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual object Log : LoggerApi {

    private fun logToNSLog(severity: Severity, tag: String?, message: String) {
        val prefix = severity.emoji
        NSLog("$prefix ${tag.orEmpty()}: $message")
    }

    actual override fun e(tag: String?, message: String, throwable: Throwable?) {
        val msg = if (throwable != null) {
            "$message\n${throwable.stackTraceToString()}"
        } else {
            message
        }
        logToNSLog(Severity.ERROR, tag, msg)
    }

    actual override fun d(tag: String?, message: String) {
        logToNSLog(Severity.DEBUG, tag, message)
    }

    actual override fun i(tag: String?, message: String) {
        logToNSLog(Severity.INFO, tag, message)
    }
}
