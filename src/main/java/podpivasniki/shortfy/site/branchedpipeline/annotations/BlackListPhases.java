package podpivasniki.shortfy.site.branchedpipeline.annotations;

import podpivasniki.shortfy.site.branchedpipeline.enums.StagePhases;

public @interface BlackListPhases {
    StagePhases[] value() default {};
}
