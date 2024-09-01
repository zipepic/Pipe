package podpivasniki.shortfy.site.branchedpipeline.json;

import lombok.Data;

@Data
public class InputsPr<T>{
    private String name;
    private Class<T> clazz;
    private T value;

    public InputsPr(String name, Class<T> clazz, T value) {
        this.name = name;
        this.clazz = clazz;
        this.value = value;
    }
}