package ru.blank.mobileappcore.network.utils

object MimeTypeResolver {
    /**
     * Определяет MIME-тип файла на основе его расширения.
     *
     * Используется для установки корректного Content-Type при отправке файла
     * на сервер через multipart-запрос. Это позволяет серверу корректно
     * обрабатывать вложенные файлы, такие как изображения, документы и т.д.
     *
     * @param filename Имя файла (может включать расширение, например "file.pdf").
     * @return MIME-тип, соответствующий расширению файла.
     * Если тип неизвестен, возвращается "application/octet-stream".
     */
    fun resolveMimeType(filename: String): String {
        val lower = filename.lowercase()
        return when {
            lower.endsWith(".pdf") -> {
                "application/pdf"
            }

            lower.endsWith(".doc") -> {
                "application/msword"
            }

            lower.endsWith(".docx") -> {
                "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
            }

            lower.endsWith(".jpg") || lower.endsWith(".jpeg") -> {
                "image/jpeg"
            }

            lower.endsWith(".png") -> {
                "image/png"
            }

            else -> "application/octet-stream"
        }
    }
}

