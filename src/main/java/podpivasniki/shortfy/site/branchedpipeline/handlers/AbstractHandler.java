package podpivasniki.shortfy.site.branchedpipeline.handlers;

import podpivasniki.shortfy.site.branchedpipeline.annotations.HandlerProcessAfter;
import podpivasniki.shortfy.site.branchedpipeline.annotations.HandlerProcessBefore;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractHandler {
    @HandlerProcessBefore
    public void fullLogInput(Object... args){
        log.debug("Before {}", args);
    }
    @HandlerProcessAfter
    public void fullLogOutputs(Object... args){
        log.debug("Outputs {}", args);
    }
}
