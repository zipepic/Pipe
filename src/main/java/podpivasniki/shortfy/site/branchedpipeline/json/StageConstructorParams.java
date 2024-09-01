package podpivasniki.shortfy.site.branchedpipeline.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class StageConstructorParams {
    @JsonProperty("object_ref")
    private String objectRef;
    @JsonProperty("api_ref")
    private String apiRef;
    @JsonProperty("subject")
    private String subject;

    public StageConstructorParamsTypes getType(){
        if(objectRef != null){
            return StageConstructorParamsTypes.OBJECT_REF;
        }
        if(apiRef != null){
            return StageConstructorParamsTypes.API_REF;
        }
        if(subject != null){
            return StageConstructorParamsTypes.SUBJECT;
        }
        throw new IllegalStateException(String.format("Not avalibale param in stage choose %s", List.of(StageConstructorParamsTypes.values())));
    }
}
