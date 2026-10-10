# Объяснение параметров WebViewConfig для iOS

## Параметры с частичной поддержкой

### `domStorageEnabled`
**Почему**: В `WKWebView` нет отдельного свойства для `domStorageEnabled`.  
**Как работает**: Если `javaScriptEnabled = true`, то `localStorage` и `sessionStorage` автоматически доступны. Если `javaScriptEnabled = false`, то DOM Storage также недоступен.

**Практическое применение**: 
- Если нужен DOM Storage, устанавливаем `javaScriptEnabled = true`
- Если нужно отключить DOM Storage, устанавливаем `javaScriptEnabled = false` (но это также отключит весь JavaScript)

**Рекомендация**: Этот параметр не имеет смысла как отдельная настройка, так как он напрямую зависит от `javaScriptEnabled`.

### `isFocusableInTouchMode`
**Почему**: iOS не различает режимы фокуса как Android.  
**Как работает**: Маппится на `isInteractive` вместе с `isFocusable`. Если `isFocusable = true`, то `isInteractive = true` независимо от значения `isFocusableInTouchMode`.

**Практическое применение**: 
- На iOS этот параметр не имеет отдельного эффекта
- Используйте `isFocusable` для управления интерактивностью

## Параметры, которые не поддерживаются

### `clearHistory`
**Почему**: `WKWebView` не предоставляет API для очистки истории навигации.

**Практическое применение**: Невозможно. История управляется системой и не может быть очищена программно.

### `allowContentAccess`
**Почему**: Это специфичная настройка Android WebView, не имеющая аналога в iOS.


### `builtInZoomControls`
**Почему**: iOS управляет зумом через стандартные жесты (pinch-to-zoom), встроенные элементы управления зумом отсутствуют.

**Практическое применение**: Не применимо для iOS. Используем `supportZoom = false` для отключения зума через жесты.
