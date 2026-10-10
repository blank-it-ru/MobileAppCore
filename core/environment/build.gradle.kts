plugins {
    alias(libs.plugins.blank.libs)
    alias(libs.plugins.blank.kotlinMultiplatform)
}

kotlin {
    sourceSets {
        commonMain {
            kotlin.srcDir(layout.buildDirectory.dir("generated/kotlin/env"))
        }
    }
}

android {
    namespace = moduleNamespace.versions.core.environment.get()
}

enum class AppFlavor { DEV, PROD }

abstract class GenerateEnvConstantsTask : DefaultTask() {
    @get:Input
    abstract val currentFlavor: Property<AppFlavor>

    @get:Input
    abstract val projectPath: Property<String>

    @get:OutputDirectory
    abstract val outputDir: DirectoryProperty

    /**
     * Управление переменными окружения для генерации конфига.
     *
     * ВНИМАНИЕ, при добавлении новых констант или методов в конфиг,
     * очень важно следить за отступами и синтаксисом в файле.
     * На выходе должен получиться корректный kotlin файл AppEnvironment.kt
     *
     * Файл будет сгенерирован в директории:
     * @see [build/generated/kotlin/env/com/blank/core/environment/AppEnvironment.kt]
     * */
    // TODO: ВНИМАНИЕ! Управление файлом конфига!
    @TaskAction
    fun generate() {
        val file = outputDir.file("${projectPath.get().replace('.', '/')}/AppEnvironment.kt")
            .get()
            .asFile

        file.parentFile.mkdirs()

        val baseApiUrl = when (currentFlavor.get()) {
            AppFlavor.PROD -> "\"\""
            AppFlavor.DEV -> "\"\""
        }

        file.writeText(
            """
            package ${projectPath.get()}

            enum class AppFlavor { DEV, PROD }

            object AppEnvironment {
                const val BASE_API_URL = $baseApiUrl
                fun getFlavor(): AppFlavor = AppFlavor.${currentFlavor.get().name}
            }
            """.trimIndent()
        )
    }
}

val generateEnvConstants by tasks.registering(GenerateEnvConstantsTask::class) {
    /**
     * TODO: ВНИМАНИЕ! Изменение файла конфига!
     * Устанавливаем нужное значение для currentFlavor, делаем это вручную!
     * После изменения значения, делаем синхронизацию проекта, далее (желательно) делаем clean,
     * после этого делаем rebuild и получаем приложение с нужным окружением.
     * */
    currentFlavor.set(AppFlavor.DEV) // Вот тут выбираем enum для нужного окружения.
    projectPath.set(libs.versions.applicationId.get())
    outputDir.set(layout.buildDirectory.dir("generated/kotlin/env"))
}

val cleanGeneratedEnv by tasks.registering(Delete::class) {
    delete(layout.buildDirectory.dir("generated/kotlin/env"))
}

kotlin.targets.forEach { target ->
    target.compilations.forEach { compilation ->
        if (compilation.name == "main") {
            tasks.named(compilation.compileKotlinTaskName) {
                dependsOn(generateEnvConstants)
            }
        }
    }
}

tasks.matching {
    with(it.name) {
        contains("build", ignoreCase = true) || contains("compile", ignoreCase = true)
    }
}.configureEach {
    dependsOn(generateEnvConstants)
}

tasks.named("clean") {
    dependsOn(cleanGeneratedEnv)
}

