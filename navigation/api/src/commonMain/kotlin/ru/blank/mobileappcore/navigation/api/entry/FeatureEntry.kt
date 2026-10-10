package ru.blank.mobileappcore.navigation.api.entry

import kotlinx.serialization.Serializable
import ru.blank.mobileappcore.navigation.api.entry.utils.EntryAnimation

interface FeatureEntry<T : @Serializable Any> {
    val route: T
    val animation: EntryAnimation
        get() = EntryAnimation.Default
}

