package podpivasniki.shortfy.site.branchedpipeline.stage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import podpivasniki.shortfy.site.branchedpipeline.json.ApiLoader;
import podpivasniki.shortfy.site.branchedpipeline.json.TemplateJson;
import podpivasniki.shortfy.site.branchedpipeline.json.TemplateParser;
import podpivasniki.shortfy.site.branchedpipeline.stats.BillingStats;
import podpivasniki.shortfy.site.branchedpipeline.testhandlers.ApiClass;

import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BillingTest {
    @Test
    @SneakyThrows
    public void testTemplateInvocationBill() {
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
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("testBilling.yaml");

        // Проверяем, что файл существует
        if (inputStream == null) {
            throw new RuntimeException("YAML file not found");
        }

        // Парсим YAML в объект TemplateJson
        TemplateJson templateJson = mapper.readValue(inputStream, TemplateJson.class);
        TemplateParser parser = TemplateParser.of(templateJson, apiLoader);
        parser.prepare();

        // Получаем стадию вызова и выполняем её
        var invocationStage = parser.getInvokationStage();
        var result = invocationStage.invoke("aaa4");

        // Проверяем результаты
        assertEquals(1, result.length, "Expected exactly one result");
        assertEquals(404, result[0], "Expected the result to be 404");
        assertEquals(100, invocationStage.popInvokeContext().getBean(BillingStats.class).getBill());
    }
}
