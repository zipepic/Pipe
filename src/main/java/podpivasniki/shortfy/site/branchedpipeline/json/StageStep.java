package podpivasniki.shortfy.site.branchedpipeline.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString

public class StageStep {
    @JsonProperty("addStages")
    private List<Stage> addStages;
}
