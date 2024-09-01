package podpivasniki.shortfy.site.branchedpipeline.handlers;

import podpivasniki.shortfy.site.branchedpipeline.annotations.HandlerProcess;
import podpivasniki.shortfy.site.branchedpipeline.args.MultiType;

public class Dubler extends AbstractHandler{
    @HandlerProcess(classes = {Object.class, Object.class})
    public MultiType proc(Object o){
        return MultiType.of(o,o);
    }
}
