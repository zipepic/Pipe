package podpivasniki.shortfy.site.branchedpipeline.args;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class HandlerArgumentWithValue extends HandlerArgument {
    private Object value;

    public Object getValue() {
        return value;
    }

    private HandlerArgumentWithValue(TypeNode typeNode, Object value) {
        super(typeNode);
        this.value = value;
    }

    public static HandlerArgumentWithValue[] ofArray(Object[] values){
        HandlerArgumentWithValue[] res = new HandlerArgumentWithValue[values.length];
        for(int i =0;i<values.length;i++){
            res[i] = HandlerArgumentWithValue.of(values[i]);
        }
        return res;
    }
    public static List<HandlerArgumentWithValue> ofArgs(Object... values){
        return Arrays.stream(values).map(HandlerArgumentWithValue::of).collect(Collectors.toList());
    }
    public static HandlerArgumentWithValue of(Object value){

        TypeNode node = TypeNode.buildTreeWithValue(value);

        return new HandlerArgumentWithValue(node, value);

    }

    @Override
    public String toString() {
        return "HandlerArgumentWithValue{" +
                "value=" + value +
                ", typeNode=" + typeNode +
                '}';
    }
}
