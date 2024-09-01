package podpivasniki.shortfy.site.branchedpipeline.stage.configchain;

import podpivasniki.shortfy.site.branchedpipeline.stage.IStageContext;
import podpivasniki.shortfy.site.branchedpipeline.stage.StageContext;

import java.util.ArrayList;
import java.util.List;

public class StageContextChain {
    private static IStageContext DEFAULT_STAGE_CONTEXT(){
        return new StageContext();
    }
    private final List<StageContextConfigHandler> handlers = new ArrayList<>();

    public StageContextChain addHandler(StageContextConfigHandler handler) {
        handlers.add(handler);
        return this;
    }

    public void process(IStageContext context) {
        for (StageContextConfigHandler handler : handlers) {
            handler.handle(context);
        }
    }
}
