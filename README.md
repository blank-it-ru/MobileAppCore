Развёртывание шаблона KMP-проекта
-
1. Настройка и конфигурация Android таргета.
- Добавить в файл local.properties данные .jks ключа:
```
    KEY_ALIAS=blank-dev
    KEY_PASSWORD=blankdev
    STORE_FILE=blank-dev.jks
    STORE_PASSWORD=blankdev
```
2. Скрипты автоматизации.
- Все скрипты для автоматизации проекта находятся в ветке:  
  [`common/scripts`](https://gitlab.com/vibelab_git/mobile-app-core/-/tree/common/scripts)

### Основные скрипты:
- `rename_packages.py` - смена base namespace в проекте

