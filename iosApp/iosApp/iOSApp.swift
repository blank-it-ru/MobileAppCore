import SwiftUI
import ComposeApp

@main
struct iOSApp: App {

    init() {
        IosKoinModuleInitializerKt.doInitKoin()
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}

