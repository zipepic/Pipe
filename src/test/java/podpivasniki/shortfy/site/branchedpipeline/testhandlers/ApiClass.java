package podpivasniki.shortfy.site.branchedpipeline.testhandlers;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class ApiClass {
    private int multipluer;

    public ApiClass(int multipluer) {
        this.multipluer = multipluer;
    }
}
