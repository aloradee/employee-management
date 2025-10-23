# Employee-management

Простое консольное приложение для управления сотрудниками, реализованное на Java с использованием ООП, работы с файлами и обработки исключений.

## Функциональность

-  Создание и управление данными сотрудников
-  Поиск и фильтрация сотрудников
-  Сохранение и загрузка данных в файл
-  Обработка исключительных ситуаций
-  Преобразование данных в различные форматы (List, Map)

employee-management/<br>
├── src/<br>
│ ├── model/<br>
│ │ └── Employee.java<br>
│ ├── service/<br>
│ │ ├── EmployeeService.java<br>
│ │ └── FileService.java<br>
│ ├── exceptions/<br>
│ │ ├── EmployeeNotFoundException.java<br>
│ │ └── FileLoadException.java<br>
│ └── Main.java<br>
└── README.md <br>



## Требования

- **Java JDK 8** или выше
- **Система сборки**: можно использовать любой способ компиляции

## Сборка и запуск

### Способ 1: Компиляция вручную через командную строку

1. **Скачайте или склонируйте проект** в удобную директорию

2. **Создайте структуру пакетов** (если нужно):
```bash
# В Linux/MacOS
mkdir -p src/model src/service src/exceptions

# В Windows
mkdir src\model src\service src\exceptions
```


Скомпилируйте Java файлы:

```bash
# Компиляция всех Java файлов
javac -d . src/model/*.java src/service/*.java src/exceptions/*.java src/Main.java

# Или компиляция по отдельности
javac src/model/Employee.java
javac src/exceptions/EmployeeNotFoundException.java
javac src/exceptions/FileLoadException.java
javac src/service/FileService.java
javac src/service/EmployeeService.java
javac src/Main.java
```

Запустите приложение:

```bash
java Main
```

## Способ 2: Использование IDE (рекомендуется)
Откройте проект в вашей IDE:

1. ### IntelliJ IDEA: File → Open → выберите папку проекта 
* Eclipse: File → Import → Existing Projects into Workspace 
* VS Code: File → Open Folder → выберите папку проекта

2. ### Убедитесь, что установлен JDK:
* В IntelliJ: File → Project Structure → Project → Project SDK
* В Eclipse: Window → Preferences → Java → Installed JREs

3. ### Скомпилируйте и запустите:
* Найдите файл Main.java
* Нажмите кнопку "Run" или используйте сочетание клавиш:
* IntelliJ: Ctrl+Shift+F10 или щелкните ▶️
* Eclipse: Ctrl+F11
* VS Code: F5 или щелкните "Run"

## Способ 3: Использование сборщика (Maven/Gradle)
### Если проект настроен для системы сборки:

Для Maven:

```bash
mvn compile
mvn exec:java -Dexec.mainClass="Main"
```
Для Gradle:

```bash
gradle build
gradle run
```
