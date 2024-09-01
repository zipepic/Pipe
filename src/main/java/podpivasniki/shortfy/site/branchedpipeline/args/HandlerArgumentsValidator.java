package podpivasniki.shortfy.site.branchedpipeline.args;

import lombok.extern.slf4j.Slf4j;
import podpivasniki.shortfy.site.branchedpipeline.enums.StagePhases;
import podpivasniki.shortfy.site.branchedpipeline.ex.StageException;

import java.util.List;
@Slf4j
public class HandlerArgumentsValidator {
    public static void validate(List<? extends HandlerArgument> expected, List<? extends HandlerArgument> actual, StagePhases stagePhases){
        log.debug("Starting validation...");
        log.debug("Expected arguments: {}", expected);
        log.debug("Actual arguments: {}", actual);

        if(actual.size() != expected.size()){
            log.error("Validation failed due to size mismatch. Expected size: {}, Actual size: {}", expected.size(), actual.size());
            throw new StageException("НЕ та длина", stagePhases);
        }

        for(int i = 0;i<expected.size();i++){
            if(!expected.get(i).equals(actual.get(i))){
                log.error("Validation failed at index {}. Expected: {}, Actual: {}", i, expected.get(i), actual.get(i));
                throw new StageException(String.format("Ошибка в валидации двух параметров ожидалось %s а полочили %S", expected.get(i), actual.get(i)), stagePhases);
            }
        }

        log.info("Validation successful Phase {}.", stagePhases);
    }
}
