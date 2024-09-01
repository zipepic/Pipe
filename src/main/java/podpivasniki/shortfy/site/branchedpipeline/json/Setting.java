package podpivasniki.shortfy.site.branchedpipeline.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Setting {
    private String name;
    @JsonProperty("class")
    private String className;
    private String value;
    @SneakyThrows
    public Object loadSetting() {
        if (value == null) {
            throw new IllegalArgumentException("Value cannot be null.");
        }

        Class<?> clazz;
        try {
            clazz = Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("Class not found: " + className, e);
        }

        return switch (clazz.getName()) {
            case "java.lang.Integer", "int" -> Integer.parseInt(value);
            case "java.lang.Long", "long" -> Long.parseLong(value);
            case "java.lang.Double", "double" -> Double.parseDouble(value);
            case "java.lang.Float", "float" -> Float.parseFloat(value);
            case "java.lang.Short", "short" -> Short.parseShort(value);
            case "java.lang.Byte", "byte" -> Byte.parseByte(value);
            case "java.lang.Boolean", "boolean" -> Boolean.parseBoolean(value);
            case "java.lang.String" -> value;
            case "java.lang.Character", "char" -> {
                if (value.length() != 1) {
                    throw new IllegalArgumentException("Cannot convert value to char: " + value);
                }
                yield value.charAt(0);
            }
            default -> throw new IllegalArgumentException("Unsupported class type: " + clazz.getName());
        };
    }
}
