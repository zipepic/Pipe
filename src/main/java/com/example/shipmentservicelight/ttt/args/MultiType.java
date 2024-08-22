package com.example.shipmentservicelight.ttt.args;

public class MultiType {
    private final Object[] args;

    private MultiType(Object[] args) {
        this.args = args;
    }


    public static MultiType of(Object... args){
        return new MultiType(args);
    }
    public Class<?>[] getClasses(){
        Class<?>[] res = new Class<?>[args.length];
        for(int i =0;i<args.length;i++){
            res[i] = args[i].getClass();
        }
        return res;
    }

    public Object[] getArgs() {
        return args;
    }
}
