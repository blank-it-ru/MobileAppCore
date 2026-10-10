package ru.blank.mobileappcore.core.logger

import ru.blank.mobileappcore.core.logger.api.LoggerApi

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual object Log : LoggerApi {
    actual override fun e(tag: String?, message: String, throwable: Throwable?) {
        android.util.Log.e(tag, message, throwable)
    }

    actual override fun d(tag: String?, message: String) {
        android.util.Log.d(tag, message)
    }

    actual override fun i(tag: String?, message: String) {
        android.util.Log.i(tag, message)
    }
}
