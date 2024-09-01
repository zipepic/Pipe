package podpivasniki.shortfy.site.branchedpipeline.stage;

import podpivasniki.shortfy.site.branchedpipeline.args.HandlerArgument;
import podpivasniki.shortfy.site.branchedpipeline.args.HandlerArgumentWithValue;
import podpivasniki.shortfy.site.branchedpipeline.ex.StageProcessInputsAreNotCorrect;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Класс {@code StageStep} представляет собой шаг внутри стадии обработки.
 * Просто ебанная обертка со своими удобными методами
 *
 * Он содержит один или несколько объектов {@link Stage} и отвечает за управление их выполнением,
 * включая валидацию входных и выходных аргументов, а также обработку результатов.
 */
@Data
class StageStep {

    /**
     * Список стадий, входящих в данный шаг.
     */
    private final List<Stage> stages;

    /**
     * Конструктор для создания шага с указанными стадиями.
     *
     * @param stages массив стадий, входящих в шаг.
     */
    StageStep(Stage... stages) {
        this.stages = Arrays.asList(stages);
    }


    /**
     * Получает список всех входных аргументов, необходимых для выполнения всех стадий в шаге.
     *
     * @return список входных аргументов.
     */
    List<HandlerArgument> getAllArgInputs(){
        return stages.stream()
                .flatMap(stage -> stage.getMainInputClasses().stream())
                .collect(Collectors.toList());
    }

    /**
     * Получает список всех выходных аргументов, полученных после выполнения всех стадий в шаге.
     *
     * @return список выходных аргументов.
     */
    List<HandlerArgument> getAllArgOutPuts(){
        return stages.stream()
                .flatMap(stage -> stage.getCurrentOutPutClasses().stream())
                .collect(Collectors.toList());
    }


    /**
     * Выполняет последовательное выполнение всех стадий в шаге с заданными входными значениями.
     *
     * @param values список входных аргументов с соответствующими значениями.
     * @return список выходных аргументов с соответствующими значениями после выполнения всех стадий.
     */
    List<HandlerArgumentWithValue> invokeStageStep(List<HandlerArgumentWithValue> values){
        return packageProcessing(values, Stage::process);
    }


    /**
     * Выполняет маппинг классов аргументов для всех стадий в шаге на основе предоставленного списка.
     *
     * @param classes список аргументов для маппинга.
     * @return список отображенных аргументов после выполнения всех стадий.
     */
    List<HandlerArgument> mappedClasses(List<HandlerArgument> classes){
        return packageProcessing(classes, Stage::mappingClasses);
    }

    /**
     * Обрабатывает аргументы для каждой стадии с использованием переданного обработчика.
     *
     * @param <T>        тип аргументов для обработки (HandlerArgument или HandlerArgumentWithValue).
     * @param <R>        тип результата обработки.
     * @param inputs     список входных аргументов.
     * @param handler    функция-обработчик для каждой стадии.
     * @return список результатов обработки.
     * @throws StageProcessInputsAreNotCorrect если количество входных аргументов не соответствует ожидаемому.
     */
    private <T, R> List<R> packageProcessing(List<T> inputs, StageProcessor<T, R> handler) {
        if (inputs.size() != getAllArgInputs().size()) {
            throw new StageProcessInputsAreNotCorrect("Не та длинна аргументов.");
        }

        List<R> results = new ArrayList<>();
        int indexOfPackage = 0;
        for (Stage stage : stages) {
            int needsParams = stage.getMainInputClasses().size();
            List<T> packagee = inputs.subList(indexOfPackage, indexOfPackage + needsParams);
            List<R> localRes = handler.process(stage, packagee);
            results.addAll(localRes);
            indexOfPackage += needsParams;
        }
        return results;
    }

}
/**
 * Интерфейс для обработки этапа.
 *
 * @param <T> тип входных аргументов.
 * @param <R> тип результата обработки.
 */
@FunctionalInterface
interface StageProcessor<T, R> {
    List<R> process(Stage stage, List<T> args);
}
