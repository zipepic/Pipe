package podpivasniki.shortfy.site.branchedpipeline.handlers;

import podpivasniki.shortfy.site.branchedpipeline.annotations.HandlerProcess;
import podpivasniki.shortfy.site.branchedpipeline.args.MultiType;

public class Swapper extends AbstractHandler{
    @HandlerProcess(classes = {Object.class, Object.class})
    public MultiType proc(Object o1, Object o2){
        return MultiType.of(o2,o1);
    }

}
