package ru.blank.mobileappcore.feature.splashScreen.presentation.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.blank.mobileappcore.feature.splashScreen.presentation.core.FeatureEntryCoreSplash
import ru.blank.mobileappcore.feature.splashScreen.presentation.core.SplashViewModel
import ru.blank.mobileappcore.feature.splashScreen.router.FeatureEntrySplash

object ModulePresentationSplashScreen {
    private fun provideEntry(): FeatureEntrySplash {
        return FeatureEntryCoreSplash()
    }

    private fun provideViewModel(): SplashViewModel {
        return SplashViewModel()
    }
    
    val module = module {
        factory<FeatureEntrySplash> { provideEntry() }
        viewModel<SplashViewModel> { provideViewModel() }
    }
}

