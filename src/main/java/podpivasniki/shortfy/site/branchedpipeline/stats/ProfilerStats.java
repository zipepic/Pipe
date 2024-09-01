package podpivasniki.shortfy.site.branchedpipeline.stats;

import lombok.Data;

@Data
public class ProfilerStats {
    private long start;
    private long end;

    @Override
    public String toString() {
        return "ProfilerStats{" +
                "start=" + start +
                ", end=" + end +
                "timeSpend=" +(end-start)+
                '}';
    }
}

