package podpivasniki.shortfy.site.branchedpipeline.stage;

import podpivasniki.shortfy.site.branchedpipeline.annotations.BlackListPhases;
import podpivasniki.shortfy.site.branchedpipeline.annotations.WhiteListPhases;
import podpivasniki.shortfy.site.branchedpipeline.enums.StagePhases;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public class PhaseValidation {
    public static void validatePhases(Method method, StagePhases currentPhase) throws Exception {

        // Проверка белого списка
        if (method.isAnnotationPresent(WhiteListPhases.class)) {
            WhiteListPhases whiteList = method.getAnnotation(WhiteListPhases.class);
            List<StagePhases> allowedPhases = Arrays.asList(whiteList.value());
            if (!allowedPhases.contains(currentPhase)) {
                throw new IllegalStateException("Current phase " + currentPhase + " is not allowed. Allowed phases: " + allowedPhases);
            }
        }

        // Проверка черного списка
        if (method.isAnnotationPresent(BlackListPhases.class)) {
            BlackListPhases blackList = method.getAnnotation(BlackListPhases.class);
            List<StagePhases> prohibitedPhases = Arrays.asList(blackList.value());
            if (prohibitedPhases.contains(currentPhase)) {
                throw new IllegalStateException("Current phase " + currentPhase + " is prohibited. Prohibited phases: " + prohibitedPhases);
            }
        }
    }
}
