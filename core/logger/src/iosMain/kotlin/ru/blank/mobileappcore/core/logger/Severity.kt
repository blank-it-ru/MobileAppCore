package ru.blank.mobileappcore.core.logger

enum class Severity(val emoji: String) {
    ERROR("🔴"),
    DEBUG("🔵"),
    INFO("🟢"),
    VERBOSE("⚪️"),
    WARN("🟡"),
    ASSERT("🟤️")
}
