### Pipe

Pipe — это Java-библиотека, которая предоставляет гибкую инфраструктуру для построения и выполнения этапов (stages) с обработчиками (handlers) в рамках сложных рабочих процессов.

## Основные возможности

Гибкое конфигурирование: Возможность настраивать и объединять различные этапы выполнения.

Инъекция зависимостей: Поддержка инъекции зависимостей для этапов с использованием контекста.

Обработка исключений: Встроенная поддержка обработки исключений на различных этапах выполнения.

Расширяемость: Легко расширяемая архитектура, которая позволяет добавлять новые обработчики и этапы.

## Установка

Склонируйте репозиторий:
sh
Копировать код
git clone <repository-url>
Перейдите в директорию проекта:
sh
Копировать код
cd botTg
Импортируйте проект в вашу IDE (например, IntelliJ IDEA или Eclipse) как Maven-проект.
Быстрый старт

## Создание собственного Handler
Для того чтобы разработать собственный обработчик (handler) в рамках BotTg, нужно создать класс, который расширяет AbstractHandler и реализует методы для обработки данных.

# Пример создания обработчика:

```java
import podpivasniki.shortfy.site.branchedpipeline.handlers.AbstractHandler;

public class MyCustomHandler extends AbstractHandler {

    @HandlerProcess
    public Object handle(Object input) {
        // Логика обработки данных
        System.out.println("Handling input: " + input);
        return process(input);
    }
}
```
## Использование обработчиков в стадии
Теперь, когда у вас есть обработчик, вы можете добавить его в стадию и выполнить:

```java
import podpivasniki.shortfy.site.branchedpipeline.stage.MainStage;
import podpivasniki.shortfy.site.branchedpipeline.handlers.HandlerMethodInvoker;

public class Main {
public static void main(String[] args) {
MyCustomHandler myHandler = new MyCustomHandler();
MainStage stage = MainStage.init(myHandler);

        // Конфигурация стадии (опционально)
        stage.build(/* передать StageContextConfigHandler */);
        stage.configure();
        // Выполнение стадии
        Object[] results = stage.invoke("Sample Input");

        for (Object result : results) {
            System.out.println("Result: " + result);
        }
    }
}
```
## Конфигурирование стадий
Вы можете настраивать стадии с использованием StageContextConfigHandler. Это позволяет гибко добавлять зависимости и другие параметры для выполнения этапов.

```java
stage.build(new MyCustomStageConfigHandler());

public class MyCustomStageConfigHandler implements StageContextConfigHandler {
@Override
public void handle(IStageContext context) {
// Добавление пользовательских параметров и зависимостей
context.registerBean(new SomeDependency());
}
}
```

## Внедрение зависимостей в handler

```java
import podpivasniki.shortfy.site.branchedpipeline.annotations.StageInject;
import podpivasniki.shortfy.site.branchedpipeline.handlers.AbstractHandler;

public class MyCustomHandler extends AbstractHandler {
    @StageInject
    SomeDependency someDependency;

    @HandlerProcess
    public Object handle(Object input) {
        // Логика обработки данных
        System.out.println("Handling input: " + input);
        return process(input);
    }
}
```
Главное не забыть передать ссылку на SomeDependency при конфигурировании Stage
## Описание yaml файлом
```yaml
settings:
  - name: _integer
    class: java.lang.Integer
    value: 101
  - name: _ImageHeight
    class: java.lang.Integer
    value: 101
  - name: _ImageWidth
    class: java.lang.Integer
    value: 101

object_setting:
  - name: _imageParams
    class: podpivasniki.shortfy.site.branchedpipeline.testhandlers.ImageParams
    setter_params:
      - name: Height
        ref: _ImageHeight
      - name: Width
        ref: _ImageWidth


base_domen: podpivasniki.shortfy.site.branchedpipeline


services:
  api:
    - podpivasniki.shortfy.site.branchedpipeline.testhandlers.ApiClass
  subject: false

stage:
  - class: .testhandlers.StringToIntWithBilling
    constructor_params:
      - object_ref: _imageParams
    steps:
      - addStages:
          - class: .handlers.Bridge
      - addStages:
          - class: .testhandlers.IntToIntWithConstructor
            constructor_params:
              - object_ref: _integer
```
Построении сдадии по файлу
```java
TemplateJson templateJson = mapper.readValue(inputStream, TemplateJson.class);
        TemplateParser parser = TemplateParser.of(templateJson, apiLoader);
        parser.prepare();

        // Получаем стадию вызова и выполняем её
        var invocationStage = parser.getInvokationStage();
        var result = invocationStage.invoke("aaa4");
```
## Примеры использования

Построение сложных цепочек обработки: Используйте различные обработчики для выполнения последовательных этапов обработки данных.

Интеграция с внешними системами: С помощью кастомных обработчиков вы можете интегрироваться с внешними сервисами, такими как базы данных, API и прочие.

Управление этапами и зависимостями: Используйте конфигурационные обработчики для гибкого управления этапами и их зависимостями.