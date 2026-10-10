package ru.blank.mobileappcore.navigation.core.di

import org.koin.core.scope.Scope
import org.koin.dsl.module
import ru.blank.mobileappcore.navigation.api.GraphBuilder
import ru.blank.mobileappcore.navigation.api.entry.FeatureEntry
import ru.blank.mobileappcore.navigation.core.GraphBuilderCore

object ModuleNavigation {
    private fun Scope.provideGraphBuilder(): GraphBuilder {
        val entries = FeatureEntries.value
            .map { kClass -> lazy { get(kClass) as FeatureEntry<*> } }
            .toSet()

        return GraphBuilderCore(entries = entries)
    }

    val module = module {
        single<GraphBuilder> { provideGraphBuilder() }
    }
}

