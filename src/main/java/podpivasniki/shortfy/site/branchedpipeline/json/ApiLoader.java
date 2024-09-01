package podpivasniki.shortfy.site.branchedpipeline.json;

public interface ApiLoader {
    default <T> T loadApiG(Class<T> clazz) {
        try {
            // Создаем и возвращаем новый экземпляр переданного класса
            return clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            // Обрабатываем исключения, которые могут возникнуть при создании экземпляра
            throw new RuntimeException("Failed to load API for class: " + clazz.getName(), e);
        }
    }
}
