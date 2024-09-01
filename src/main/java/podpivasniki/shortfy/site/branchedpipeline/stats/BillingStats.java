package podpivasniki.shortfy.site.branchedpipeline.stats;

import lombok.Getter;
import lombok.ToString;

@ToString
public class BillingStats {
    @Getter
    private int bill = 0;
    public void increment(int inc){
        if(inc < 0)
            throw new RuntimeException("Incremetn must by higth with zero");
        bill+=inc;
    }
}
