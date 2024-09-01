package podpivasniki.shortfy.site.branchedpipeline.stage;

import podpivasniki.shortfy.site.branchedpipeline.stage.configchain.StageContextConfigHandler;

public interface ConfigurableStage {
    InvocationStage configure(StageContextConfigHandler... handlers);
}
