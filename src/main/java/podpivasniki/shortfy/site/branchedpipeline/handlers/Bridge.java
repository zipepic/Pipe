package podpivasniki.shortfy.site.branchedpipeline.handlers;

import podpivasniki.shortfy.site.branchedpipeline.annotations.HandlerProcess;

public class Bridge extends AbstractHandler {
    @HandlerProcess
    public Object proc(Object o){
        return o;
    }
}
