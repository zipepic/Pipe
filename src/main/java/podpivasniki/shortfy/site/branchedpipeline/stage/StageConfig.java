package podpivasniki.shortfy.site.branchedpipeline.stage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import podpivasniki.shortfy.site.branchedpipeline.enums.StageMode;

import java.util.UUID;
@AllArgsConstructor
@Data
public class StageConfig {
    private final UUID stageUUID;
    private final StageMode stageMode;

    public static StageConfig defaultStageConfig(){
        return new StageConfig(UUID.randomUUID(), StageMode.PROCESSING);
    }
}
