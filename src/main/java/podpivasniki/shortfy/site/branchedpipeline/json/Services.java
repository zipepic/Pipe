package podpivasniki.shortfy.site.branchedpipeline.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import java.util.List;
@ToString
@Data
public class Services {
    @JsonProperty("api")
    private List<String> apiClasses;
    @JsonProperty("subject")
    private boolean allowSubject;
}
