package podpivasniki.shortfy.site.branchedpipeline.stage;

import podpivasniki.shortfy.site.branchedpipeline.testhandlers.ApiClass;
import podpivasniki.shortfy.site.branchedpipeline.json.ApiLoader;
import podpivasniki.shortfy.site.branchedpipeline.json.TemplateJson;
import podpivasniki.shortfy.site.branchedpipeline.json.TemplateParser;
import podpivasniki.shortfy.site.branchedpipeline.observers.Observer;
import podpivasniki.shortfy.site.branchedpipeline.observers.StageSubject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PsrserTest {
    @Test
    @SneakyThrows
    public void testTemplateInvocation() {
        // Создание объекта загрузчика API с пустой реализацией метода loadApi
        ApiLoader apiLoader = new ApiLoader() {
            @Override
            public <T> T loadApiG(Class<T> clazz) {
                return (T) new ApiClass(1);
            }
        };

        // Настройка ObjectMapper для работы с YAML
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

        // Загружаем файл YAML из ресурсов
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("test.yaml");

        // Проверяем, что файл существует
        if (inputStream == null) {
            throw new RuntimeException("YAML file not found");
        }

        // Парсим YAML в объект TemplateJson
        TemplateJson templateJson = mapper.readValue(inputStream, TemplateJson.class);

        // Создаем TemplateParser и загружаем входные данные
        Observer observer = new Observer() {
            StageSubject subject;
            @Override
            public void update() {
                System.out.println(subject.getState());
            }

            @Override
            public void setSubject(StageSubject subject) {
                this.subject = subject;
            }
        };

        TemplateParser parser = TemplateParser.of(templateJson, apiLoader);
        parser.setupStageObservers(Arrays.asList(observer));
        parser.prepare();

        // Получаем стадию вызова и выполняем её
        var invocationStage = parser.getInvokationStage();
        var result = invocationStage.invoke("aaa4");

        // Проверяем результаты
        assertEquals(1, result.length, "Expected exactly one result");
        assertEquals(404, result[0], "Expected the result to be 404");
    }

}
